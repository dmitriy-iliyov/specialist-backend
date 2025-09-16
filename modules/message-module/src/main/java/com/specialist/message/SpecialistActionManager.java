package com.specialist.message;

import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;

public interface SpecialistActionManager {
    void process(SpecialistActionEvent event);
}
