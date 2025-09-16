package com.messageservice.infrastructure;

import com.messageservice.services.SpecialistActionEventHandler;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class SpecialistActionKafkaListener {

    private final SpecialistActionEventHandler processor;

    @KafkaListener(topics = "${api.kafka.topic.specialist-action}", groupId = "${api.kafka.group_id.specialist-action}")
    public void listen(SpecialistActionEvent event) throws Exception {
        processor.handel(event);
    }
}
