package com.specialist.specialistdirectory.domain.specialist.services.specialist;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface ManagedSpecialistOrchestrator {
    SpecialistResponseDto update(SpecialistUpdateDto dto);
    void delete(UUID accountId, UUID id);
}
