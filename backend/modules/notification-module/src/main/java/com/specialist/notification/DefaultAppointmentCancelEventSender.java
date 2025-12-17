package com.specialist.notification;

import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultAppointmentCancelEventSender implements AppointmentCancelEventSender {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void sendEvents(List<ExternalAppointmentCancelEvent> events) {
        events.forEach(eventPublisher::publishEvent);
    }
}
