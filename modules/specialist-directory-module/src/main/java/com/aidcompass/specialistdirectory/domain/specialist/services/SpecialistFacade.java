package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface SpecialistFacade {
    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void delete(UUID creatorId, UUID id);

    SpecialistResponseDto save(SpecialistCreateDto dto);
}
