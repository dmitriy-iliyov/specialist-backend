package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.contracts.user.CreatorRatingUpdateEvent;
import com.specialist.specialistdirectory.domain.review.mappers.ReviewBufferMapper;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewEventSender {

    @Value("${api.review-buffer.clean.batch-size}")
    public int CLEAN_BATCH_SIZE;

    @Value("${api.kafka.topic.creator-rating}")
    public String TOPIC;

    private final ReviewBufferService service;
    private final ReviewBufferMapper mapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final int MAX_ATTEMPT_COUNT = 2;

    @Scheduled(
            initialDelayString = "${api.review-buffer.clean.initial_delay}",
            fixedDelayString = "${api.review-buffer.clean.fixed_delay}"
    )
    public void sendEventsBatch() {
        List<CreatorRatingUpdateEvent> events = mapper.toEventList(
                service.findBatchByDeliveryState(DeliveryState.READY_TO_SEND, CLEAN_BATCH_SIZE)
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
                .thenAccept(success -> service.markAsSent(event.id()))
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
}
