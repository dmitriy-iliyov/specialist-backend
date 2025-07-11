package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.*;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.aidcompass.specialistdirectory.utils.PageRequest;
import com.aidcompass.specialistdirectory.utils.PageResponse;

import java.util.UUID;

public interface SpecialistService {
    SpecialistResponseDto save(SpecialistCreateDto dto);

    UUID getCreatorIdById(UUID id);

    SpecialistResponseDto findById(UUID creatorId, UUID id);

    SpecialistResponseDto update(SpecialistUpdateDto dto);

    SpecialistResponseDto findById(UUID id);

    void updateAllByTypeIdPair(Long oldTypeId, Long newTypeId);

    void deleteById(UUID id);

    PageResponse<SpecialistResponseDto> findAllByRatingDesc(PageRequest page);

    PageResponse<SpecialistResponseDto> findAllByFilter(SpecialistFilter filter);

    PageResponse<SpecialistResponseDto> findAllByCreatorId(UUID creatorId, PageRequest page);

    PageResponse<SpecialistResponseDto> findAllByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter);
}