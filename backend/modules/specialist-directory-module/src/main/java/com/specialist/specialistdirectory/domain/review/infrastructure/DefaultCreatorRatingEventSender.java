package com.specialist.specialistdirectory.domain.review.infrastructure;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.specialist.specialistdirectory.domain.review.services.CreatorRatingBufferService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DefaultCreatorRatingEventSender implements CreatorRatingEventSender {

    private final ApplicationEventPublisher eventPublisher;
    private final CreatorRatingBufferService service;

    @Transactional
    @Override
    public void sendEvent(CreatorRatingUpdateEvent event, int attemptNumber) {
        eventPublisher.publishEvent(event);
        service.updateDeliveryStateById(event.id(), DeliveryState.SENT);
    }
}
