package com.messageservice.services;

import com.messageservice.exceptions.NullSpecialistRecallMessageServiceException;
import com.specialist.contracts.specialistdirectory.dto.ActionType;
import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public void orchestrate(SpecialistActionEvent event) throws Exception {
        SpecialistRecallMessageService service = serviceMap.get(event.contactType());
        if (service == null) {
            log.error("SpecialistRecallMessageService not found for contact type {}", event.contactType());
            throw new NullSpecialistRecallMessageServiceException();
        }
        service.sendMessage(event);
    }
}
