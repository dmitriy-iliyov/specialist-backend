package com.messageservice.infrastructure;

import com.messageservice.services.AppointmentCancelHandler;
import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class AppointmentCancelKafkaListener {

    private final AppointmentCancelHandler appointmentCancelHandler;

    @KafkaListener(topics = "${api.kafka.topic.cancel-appointment}", groupId = "${api.kafka.group_id}")
    public void listen(ExternalAppointmentCancelEvent event) throws Exception {
        appointmentCancelHandler.handle(event);
    }
}
