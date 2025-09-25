package com.specialist.notification;

import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaAppointmentCancelEventSender implements AppointmentCancelEventSender {

    @Value("${api.kafka.topic.cancel-appointment}")
    private String TOPIC;
    private final KafkaTemplate<String, ExternalAppointmentCancelEvent> kafkaTemplate;

    @Override
    public void sendEvents(List<ExternalAppointmentCancelEvent> events) {
        kafkaTemplate.executeInTransaction(callback -> {
            events.forEach(event -> kafkaTemplate.send(TOPIC, event));
            return null;
        });
    }
}
