package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.review.models.enums.RatingOperationType;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface SpecialistOrchestrator {
    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void updateRatingById(UUID id, long rating, RatingOperationType operationType);

    void delete(UUID creatorId, UUID id);

    SpecialistResponseDto save(SpecialistCreateDto dto);
}
