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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewBufferServiceImpl implements ReviewBufferService {

    @Value("${api.review-buffer.batch-size}")
    public int reviewBatchSize;

    @Value("${api.review-buffer.clean.batch-size}")
    public int cleanBatchSize;

    @Value("${api.kafka.topic.review-buffer}")
    public String topic;

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
            if (specialistReviewCount >= reviewBatchSize) {
                entity.setDeliveryState(DeliveryState.READY);
            }
        }
        repository.save(entity);
    }

    @Transactional
    @Override
    public void pop(UUID creatorId) {
        repository.deleteByCreatorId(creatorId);
    }

    @Scheduled(
            initialDelayString = "${api.review-buffer.clean.initialDelay}",
            fixedDelayString = "${api.review-buffer.clean.fixedDelay}"
    )
    public void clean() {
        List<CreatorRatingUpdateEvent> events = mapper.toEventList(
                repository.findAllByDeliveryState(DeliveryState.READY, Pageable.ofSize(cleanBatchSize)).getContent()
        );
        for (CreatorRatingUpdateEvent event: events) {
            this.sendEvent(event, 1);
        }
    }

    private void sendEvent(CreatorRatingUpdateEvent event, int attemptNumber) {
        kafkaTemplate.send(topic, event)
                .thenAccept(success -> self.pop(event.creatorId()))
                .exceptionally(fail -> {
                    log.error("Error publishing CreatorRatingUpdateEvent with creatorId={}, date={}, time={}",
                            event.creatorId(), LocalDate.now(), LocalTime.now());
                    if (attemptNumber <= MAX_ATTEMPT_COUNT) {
                        int incrementedAttemptCount = attemptNumber + 1;
                        sendEvent(event, incrementedAttemptCount);
                        return null;
                    }
                    log.error("Failed all attempt to publish CreatorRatingUpdateEvent with creatorId={}, date={}, time={}",
                            event.creatorId(), LocalDate.now(), LocalTime.now());
                    return null;
                });
    }
}
