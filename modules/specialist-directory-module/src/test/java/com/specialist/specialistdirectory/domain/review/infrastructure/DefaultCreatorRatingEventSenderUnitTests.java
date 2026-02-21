package com.specialist.specialistdirectory.domain.review.infrastructure;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultCreatorRatingEventSenderUnitTests {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private CreatorRatingBufferService service;

    @InjectMocks
    private DefaultCreatorRatingEventSender sender;

    @Test
    @DisplayName("UT: sendEvent() should publish event and update state")
    void sendEvent_shouldPublishAndUpdate() {
        CreatorRatingUpdateEvent event = new CreatorRatingUpdateEvent(UUID.randomUUID(), UUID.randomUUID(), 5, 1);

        sender.sendEvent(event, 1);

        verify(eventPublisher).publishEvent(event);
        verify(service).updateDeliveryStateById(event.id(), DeliveryState.SENT);
    }
}
