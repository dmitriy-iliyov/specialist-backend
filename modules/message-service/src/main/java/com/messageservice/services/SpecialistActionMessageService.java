package com.messageservice.services;

import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;

public interface SpecialistActionMessageService extends ContactTypeHolder {
    void sendMessage(SpecialistActionEvent event) throws Exception;
}
