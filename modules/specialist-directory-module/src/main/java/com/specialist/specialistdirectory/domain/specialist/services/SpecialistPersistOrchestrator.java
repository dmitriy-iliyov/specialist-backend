package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;

import java.util.UUID;

public interface SpecialistPersistOrchestrator {
    SpecialistResponseDto save(UUID creatorId, CreatorType creatorType, SpecialistCreateDto dto);
}
