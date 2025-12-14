package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageResponse;

import java.util.Set;
import java.util.UUID;

public interface SpecialistService {
    SpecialistResponseDto save(SpecialistCreateDto dto);

    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void updateAllByTypeIdPair(Long oldTypeId, Long newTypeId);

    ShortSpecialistInfo getShortInfoById(UUID id);

    SpecialistResponseDto findById(UUID id);

    SpecialistResponseDto findByCreatorIdAndId(UUID creatorId, UUID id);

    PageResponse<SpecialistResponseDto> findAll(PageDataHolder page);

    PageResponse<SpecialistResponseDto> findAllByFilter(SpecialistFilter filter);

    PageResponse<SpecialistResponseDto> findAllByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter);

    void deleteById(UUID id);

    void deleteByOwnerId(UUID ownerId);

    void deleteAllByOwnerIds(Set<UUID> ownerIds);
}