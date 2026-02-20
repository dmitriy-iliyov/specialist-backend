package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateRequest;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface SpecialistManagementStrategy {
    ProfileType getType();

    SpecialistResponseDto save(SpecialistCreateRequest request);

    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void delete(UUID accountId, UUID id);
}
