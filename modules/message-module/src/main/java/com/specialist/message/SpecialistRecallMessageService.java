package com.specialist.message;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.contracts.specialistdirectory.dto.SpecialistActionEvent;

public interface SpecialistRecallMessageService {
    ContactType getContactType();
    void sendMessage(SpecialistActionEvent event);
}
