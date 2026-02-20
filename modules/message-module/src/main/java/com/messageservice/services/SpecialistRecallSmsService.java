package com.messageservice.services;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpecialistRecallSmsService implements SpecialistRecallMessageService {

    @Override
    public void sendMessage(SpecialistActionEvent event) {
        log.warn("Need twilio");
    }

    @Override
    public ContactType getContactType() {
        return ContactType.PHONE_NUMBER;
    }
}
