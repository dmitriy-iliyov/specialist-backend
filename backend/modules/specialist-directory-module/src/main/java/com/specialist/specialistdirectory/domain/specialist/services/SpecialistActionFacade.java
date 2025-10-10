package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface SpecialistActionFacade {
    void recallRequest(UUID id, ContactType contactType);

    void recall(String code);

    void manageRequest(UUID id, UUID accountId, ContactType contactType);

    void manage(UUID accountId, String code, HttpServletRequest request, HttpServletResponse response);
}
