package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.user.ProfileType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface SpecialistManagementOrchestrator {
    SpecialistResponseDto save(UUID creatorId, ProfileType type, SpecialistCreateDto dto);

    SpecialistResponseDto update(SpecialistUpdateDto dto, ProfileType type);

    void delete(UUID accountId, UUID id, ProfileType type);
}
