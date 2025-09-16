package com.specialist.message;

import com.specialist.contracts.specialistdirectory.dto.ActionType;
import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import com.specialist.message.core.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SpecialistRecallOrchestrator implements SpecialistActionOrchestrator {

    private final Map<ContactType, SpecialistRecallMessageService> serviceMap;

    public SpecialistRecallOrchestrator(List<SpecialistRecallMessageService> services) {
        this.serviceMap = services.stream()
                .collect(Collectors.toMap(SpecialistRecallMessageService::getContactType, Function.identity()));
    }

    @Override
    public ActionType getActionType() {
        return ActionType.RECALL;
    }

    @Override
    public void orchestrate(SpecialistActionEvent event) {

    }
}
