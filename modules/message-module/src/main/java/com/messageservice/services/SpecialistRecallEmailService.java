package com.messageservice.services;

import com.messageservice.models.MessageDto;
import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SpecialistRecallEmailService implements SpecialistRecallMessageService {

    private final MessageService service;

    public SpecialistRecallEmailService(@Qualifier("notificationEmailService") MessageService service) {
        this.service = service;
    }

    @Override
    public ContactType getContactType() {
        return ContactType.EMAIL;
    }

    @Override
    public void sendMessage(SpecialistActionEvent event) throws Exception {
        service.sendMessage(new MessageDto(event.contact(), "Recall specialist.", event.code()));
    }
}
