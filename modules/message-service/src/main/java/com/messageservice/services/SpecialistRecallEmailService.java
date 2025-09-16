package com.specialist.message.service.services;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import com.specialist.message.service.models.MessageDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SpecialistRecallEmailService implements SpecialistRecallMessageService {

    private final MessageService service;

    public SpecialistRecallEmailService(@Qualifier("emailService") MessageService service) {
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
