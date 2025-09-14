package com.specialist.specialistdirectory.domain.specialist.services.creator;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface CreatorSpecialistOrchestrator {
    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void delete(UUID creatorId, UUID id);
}
