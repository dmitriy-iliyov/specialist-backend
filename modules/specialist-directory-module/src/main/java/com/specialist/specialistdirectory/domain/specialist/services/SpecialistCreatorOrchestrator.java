package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface SpecialistCreatorOrchestrator {
    SpecialistResponseDto save(SpecialistCreateDto dto);

    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void delete(UUID creatorId, UUID id);
}
