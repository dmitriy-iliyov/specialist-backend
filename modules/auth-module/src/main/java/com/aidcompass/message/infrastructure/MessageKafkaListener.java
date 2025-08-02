package com.aidcompass.message.infrastructure;

import com.aidcompass.message.models.EmailConfirmationEvent;
import com.aidcompass.message.services.ConfirmationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageKafkaListener {

    private final ConfirmationService service;

    public MessageKafkaListener(@Qualifier("confirmationServiceDecorator") ConfirmationService service) {
        this.service = service;
    }

    @KafkaListener(topics = {"${api.kafka.topic.message}"})
    public void listen(EmailConfirmationEvent event) {
        service.sendConfirmationMessage(event.id(), event.email());
    }
}
