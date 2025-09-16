package com.messageservice.services;

import com.messageservice.exceptions.NullSpecialistManageMessageServiceException;
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
public class SpecialistManageOrchestrator implements SpecialistActionOrchestrator {

    private final Map<ContactType, SpecialistManageMessageService> serviceMap;

    public SpecialistManageOrchestrator(List<SpecialistManageMessageService> services) {
        this.serviceMap = services.stream()
                .collect(Collectors.toMap(SpecialistManageMessageService::getContactType, Function.identity()));
    }

    @Override
    public ActionType getActionType() {
        return ActionType.MANAGE;
    }

    @Override
    public void orchestrate(SpecialistActionEvent event) throws Exception {
        SpecialistManageMessageService service = serviceMap.get(event.contactType());
        if (service == null) {
            log.error("SpecialistManageMessageService not found for contact type {}", event.contactType());
            throw new NullSpecialistManageMessageServiceException();
        }
        service.sendMessage(event);
    }
}
