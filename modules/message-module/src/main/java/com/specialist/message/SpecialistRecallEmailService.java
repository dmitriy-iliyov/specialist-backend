package com.specialist.message;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import com.specialist.message.core.MessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SpecialistRecallEmailService implements SpecialistRecallMessageService {

    private final MessageService service;

    public SpecialistRecallEmailService(@Qualifier("emailService")MessageService service) {
        this.service = service;
    }

    @Override
    public ContactType getContactType() {
        return ContactType.EMAIL;
    }

    @Override
    public void sendMessage(SpecialistActionEvent event) {
        //service.sendMessage();
    }
}
