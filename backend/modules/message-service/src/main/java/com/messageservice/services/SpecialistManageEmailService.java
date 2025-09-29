package com.messageservice.services;

import com.messageservice.models.MessageDto;
import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SpecialistManageEmailService implements SpecialistManageMessageService {

    private final MessageService service;

    public SpecialistManageEmailService(@Qualifier("emailService") MessageService service) {
        this.service = service;
    }

    @Override
    public void sendMessage(SpecialistActionEvent event) throws Exception {
        service.sendMessage(new MessageDto(event.contact(), "Manage specialist request.", event.code()));
    }

    @Override
    public ContactType getContactType() {
        return ContactType.EMAIL;
    }
}
