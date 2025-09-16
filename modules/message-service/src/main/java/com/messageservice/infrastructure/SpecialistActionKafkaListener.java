package com.specialist.message.service.infrastructure;

import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import com.specialist.message.service.services.SpecialistActionEventProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class SpecialistActionKafkaListener {

    private final SpecialistActionEventProcessor processor;

    @KafkaListener(topics = {"${api.kafka.topic.specialist-action}"}, groupId = "message-service")
    public void listen(SpecialistActionEvent event) throws Exception {
        processor.process(event);
    }
}
