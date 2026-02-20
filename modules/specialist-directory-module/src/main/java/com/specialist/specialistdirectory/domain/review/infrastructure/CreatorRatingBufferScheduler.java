package com.specialist.specialistdirectory.domain.review.infrastructure;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.specialistdirectory.domain.review.mappers.ReviewBufferMapper;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatorRatingBufferScheduler {

    @Value("${api.creator-rating-buffer.clean.batch-size}")
    public int CLEAN_BATCH_SIZE;

    public Long ACTUALIZE_BATCH_SIZE;

    public Long TTL_AFTER_LAST_UPDATE;

    private final CreatorRatingBufferService service;
    private final ReviewBufferMapper mapper;
    private final CreatorRatingEventSender eventSender;

    @Scheduled(
            initialDelayString = "${api.creator-rating-buffer.clean.initial_delay}",
            fixedDelayString = "${api.creator-rating-buffer.clean.fixed_delay}"
    )
    public void sendBatch() {
        List<CreatorRatingUpdateEvent> events = mapper.toEventList(
                service.findAllByDeliveryState(DeliveryState.READY_TO_SEND, CLEAN_BATCH_SIZE)
        );
        if (events.isEmpty()) {
            return;
        }
        for (CreatorRatingUpdateEvent event: events) {
            eventSender.sendEvent(event, 1);
        }
    }

    @Scheduled(
            initialDelayString = "${api.creator-rating-buffer.delivery-state.actualize.initial_delay}",
            fixedDelayString = "${api.creator-rating-buffer.delivery-state.actualize.fixed_delay}"
    )
    public void actualizeState() {
        Instant beforeDate = Instant.now().minusSeconds(TTL_AFTER_LAST_UPDATE);
        try {
            service.updateBatchDeliveryStateByDeliveryStateAndUpdateBefore(
                    DeliveryState.PREPARE, DeliveryState.READY_TO_SEND, beforeDate, ACTUALIZE_BATCH_SIZE
            );
        } catch (Exception e) {
            log.error("Error when actualize DeliveryState in creator_rating_buffers", e);
            throw e;
        }
    }
}
