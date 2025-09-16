package com.specialist.message.infrastructure;

import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import com.specialist.message.SpecialistActionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class SpecialistActionKafkaListener {

    private final SpecialistActionManager orchestrator;

    @KafkaListener(topics = {})
    public void listen(SpecialistActionEvent event) {
        orchestrator.process(event);
    }
}
