package com.messageservice.services;

import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;

public interface SpecialistActionEventHandler {
    void handel(SpecialistActionEvent event) throws Exception;
}
