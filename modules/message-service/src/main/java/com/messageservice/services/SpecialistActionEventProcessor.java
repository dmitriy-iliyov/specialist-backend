package com.specialist.message.service.services;

import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;

public interface SpecialistActionEventProcessor {
    void process(SpecialistActionEvent event) throws Exception;
}
