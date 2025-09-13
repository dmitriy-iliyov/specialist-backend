package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.user.UserType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface SpecialistManagementOrchestrator {
    SpecialistResponseDto update(SpecialistUpdateDto dto, UserType type);
    void delete(UUID accountId, UUID id, UserType type);
}
