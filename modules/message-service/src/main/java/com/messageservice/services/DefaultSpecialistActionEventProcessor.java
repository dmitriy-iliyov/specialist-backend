package com.specialist.message.service.services;

import com.specialist.contracts.specialistdirectory.dto.ActionType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import com.specialist.message.service.exceptions.NullSpecialistActionOrchestratorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DefaultSpecialistActionEventProcessor implements SpecialistActionEventProcessor {

    private final Map<ActionType, SpecialistActionOrchestrator> orchestratorsMap;

    public DefaultSpecialistActionEventProcessor(List<SpecialistActionOrchestrator> orchestrators) {
        this.orchestratorsMap = orchestrators.stream()
                .collect(Collectors.toMap(SpecialistActionOrchestrator::getActionType, Function.identity()));
    }

    @Override
    public void process(SpecialistActionEvent event) throws Exception {
        SpecialistActionOrchestrator orchestrator = orchestratorsMap.get(event.type());
        if (orchestrator == null) {
            log.error("SpecialistActionOrchestrator not found for action type {}", event.type());
            throw new NullSpecialistActionOrchestratorException();
        }
        orchestrator.orchestrate(event);
    }
}
