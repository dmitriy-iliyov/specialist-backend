package com.messageservice.services;

import com.specialist.contracts.specialistdirectory.dto.ActionType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;

public interface SpecialistActionOrchestrator {
    ActionType getActionType();
    void orchestrate(SpecialistActionEvent event) throws Exception;
}
