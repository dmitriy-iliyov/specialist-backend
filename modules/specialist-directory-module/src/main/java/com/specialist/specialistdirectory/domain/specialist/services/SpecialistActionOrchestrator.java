package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.enums.ContactType;

import java.util.UUID;

public interface SpecialistActionOrchestrator {
    void recallRequest(UUID id, ContactType contactType);

    void recall(String code);

    void manageRequest(UUID id, UUID accountId, ContactType contactType);

    void manage(String code);
}
