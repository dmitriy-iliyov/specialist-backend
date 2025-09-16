package com.specialist.message;

import com.specialist.contracts.specialistdirectory.dto.ActionType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;

public interface SpecialistActionOrchestrator {
    ActionType getActionType();
    void orchestrate(SpecialistActionEvent event);
}
