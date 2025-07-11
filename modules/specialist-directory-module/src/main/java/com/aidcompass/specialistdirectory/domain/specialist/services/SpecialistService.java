package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;

import java.util.UUID;

public interface SpecialistService {
    SpecialistResponseDto save(SpecialistCreateDto dto);

    //@Transactional(readOnly = true)
    UUID getCreatorIdById(UUID id);

    SpecialistResponseDto findById(UUID creatorId, UUID id);

    SpecialistResponseDto update(SpecialistUpdateDto dto);

    SpecialistResponseDto findById(UUID id);

    void updateAllByTypeIdPair(Long oldTypeId, Long newTypeId);

    void delete(UUID id);
}