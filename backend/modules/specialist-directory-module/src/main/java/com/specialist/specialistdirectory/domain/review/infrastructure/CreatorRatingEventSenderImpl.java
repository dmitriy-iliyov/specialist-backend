package com.specialist.specialistdirectory.domain.review.infrastructure;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.specialist.specialistdirectory.domain.review.services.CreatorRatingBufferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatorRatingEventSenderImpl implements CreatorRatingEventSender {

    @Value("${api.kafka.topic.creator-rating}")
    public String TOPIC;

    private final CreatorRatingBufferService service;
    private final KafkaTemplate<String, CreatorRatingUpdateEvent> kafkaTemplate;
    private static final int MAX_ATTEMPT_COUNT = 2;

    @Override
    public void sendEvent(CreatorRatingUpdateEvent event, int attemptNumber) {
        kafkaTemplate.send(TOPIC, event)
                .thenAccept(success -> service.updateDeliveryStateById(event.id(), DeliveryState.SENT))
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
