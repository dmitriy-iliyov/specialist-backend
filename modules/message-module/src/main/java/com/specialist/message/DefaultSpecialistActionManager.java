package com.specialist.message;

import com.specialist.contracts.specialistdirectory.dto.ActionType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DefaultSpecialistActionManager implements SpecialistActionManager {

    private final Map<ActionType, SpecialistActionOrchestrator> orchestratorsMap;

    public DefaultSpecialistActionManager(List<SpecialistActionOrchestrator> orchestrators) {
        this.orchestratorsMap = orchestrators.stream()
                .collect(Collectors.toMap(SpecialistActionOrchestrator::getActionType, Function.identity()));
    }

    @Override
    public void process(SpecialistActionEvent event) {
        SpecialistActionOrchestrator orchestrator = orchestratorsMap.get(event.type());
        if (orchestrator == null) {
            throw new RuntimeException();
        }
        orchestrator.orchestrate(event);
    }
}
