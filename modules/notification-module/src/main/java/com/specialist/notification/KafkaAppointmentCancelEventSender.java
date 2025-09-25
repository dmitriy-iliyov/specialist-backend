package com.specialist.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaExternalAppointmentCancelEventSender implements ExternalAppointmentCancelEventSender {

    @Value("${}")
    private String TOPIC;
    private final KafkaTemplate<String, AppointmentCancelEvent> kafkaTemplate;
}
