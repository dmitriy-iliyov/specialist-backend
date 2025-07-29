package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;
import com.aidcompass.specialistdirectory.domain.review.mappers.ReviewBufferMapper;
import com.aidcompass.specialistdirectory.domain.review.models.ReviewBufferEntity;
import com.aidcompass.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.aidcompass.specialistdirectory.domain.review.repositories.ReviewBufferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewBufferServiceImpl implements ReviewBufferService {

    @Value("${api.review-buffer.size}")
    public int REVIEW_BUFFER_SIZE;

    @Value("${api.review-buffer.clean.batch-size}")
    public int CLEAN_BATCH_SIZE;

    @Value("${api.kafka.topic.creator-rating}")
    public String TOPIC;

    private final ReviewBufferService self;
    private final ReviewBufferRepository repository;
    private final ReviewBufferMapper mapper;
    private final KafkaTemplate<String, CreatorRatingUpdateEvent> kafkaTemplate;
    private static final int MAX_ATTEMPT_COUNT = 2;


    @Transactional
    @Override
    public void put(UUID creatorId, long rating) {
        ReviewBufferEntity entity = repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)
                .orElse(null);
        if (entity == null) {
            entity = new ReviewBufferEntity(creatorId, rating);
        } else {
            long earnedSpecialistRating = entity.getSummaryRating() + rating;
            long specialistReviewCount = entity.getReviewCount() + 1;
            entity.setSummaryRating(earnedSpecialistRating);
            entity.setReviewCount(specialistReviewCount);
            if (specialistReviewCount >= REVIEW_BUFFER_SIZE) {
                entity.setDeliveryState(DeliveryState.READY_TO_SEND);
            }
        }
        repository.save(entity);
    }

    @Transactional
    @Override
    public void markAsSent(UUID id) {
        repository.updateDeliveryStateById(DeliveryState.SENT, id);
    }

    @Scheduled(
            initialDelayString = "${api.review-buffer.clean.initial_delay}",
            fixedDelayString = "${api.review-buffer.clean.fixed_delay}"
    )
    @Transactional
    @Override
    public void sendEventsBatch() {
        List<CreatorRatingUpdateEvent> events = mapper.toEventList(
                repository.findAllByDeliveryState(DeliveryState.READY_TO_SEND, Pageable.ofSize(CLEAN_BATCH_SIZE)).getContent()
        );
        if (events.isEmpty()) {
            return;
        }
        for (CreatorRatingUpdateEvent event: events) {
            this.sendEvent(event, 1);
        }
    }

    private void sendEvent(CreatorRatingUpdateEvent event, int attemptNumber) {
        kafkaTemplate.send(TOPIC, event)
                .thenAccept(success -> self.markAsSent(event.id()))
                .exceptionally(fail -> {
                    log.error("Error publishing CreatorRatingUpdateEvent with creatorId={}, date={}, time={}",
                            event.creatorId(), LocalDate.now(), LocalTime.now());
                    if (attemptNumber <= MAX_ATTEMPT_COUNT) {
                        int nextAttempt = attemptNumber + 1;
                        CompletableFuture.delayedExecutor(100, TimeUnit.MILLISECONDS).execute(() ->
                                sendEvent(event, nextAttempt)
                        );
                        return null;
                    }
                    log.error("Failed all attempt to publish CreatorRatingUpdateEvent with creatorId={}, date={}, time={}",
                            event.creatorId(), LocalDate.now(), LocalTime.now());
                    return null;
                });
    }

    @Transactional
    @Override
    public void popAllByIdIn(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        repository.deleteAllByIdIn(ids);
    }

    @Transactional
    @Override
    public void markBatchAsReadyToSend(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        repository.updateAllDeliveryStatesByIdIn(DeliveryState.READY_TO_SEND, ids);
    }
}
