package com.messageservice.services;

import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;

public interface SpecialistActionEventProcessor {
    void process(SpecialistActionEvent event) throws Exception;
}
