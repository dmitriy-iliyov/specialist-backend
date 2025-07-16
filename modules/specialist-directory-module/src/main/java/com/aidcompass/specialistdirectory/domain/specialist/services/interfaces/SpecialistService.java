package com.aidcompass.specialistdirectory.domain.specialist.services.interfaces;

import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.*;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;

import java.util.UUID;

public interface SpecialistService {
    SpecialistResponseDto save(SpecialistCreateDto dto);

    UUID getCreatorIdById(UUID id);

    SpecialistResponseDto findByCreatorIdAndId(UUID creatorId, UUID id);

    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void updateRatingById(UUID id, long rating);

    SpecialistResponseDto findById(UUID id);

    void updateAllByTypeIdPair(Long oldTypeId, Long newTypeId);

    void deleteById(UUID id);

    PageResponse<SpecialistResponseDto> findAll(PageRequest page);

    PageResponse<SpecialistResponseDto> findAllByFilter(SpecialistFilter filter);

    PageResponse<SpecialistResponseDto> findAllByCreatorId(UUID creatorId, PageRequest page);

    PageResponse<SpecialistResponseDto> findAllByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter);
}