package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ShortSpecialistInfo;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface SpecialistService {
    SpecialistResponseDto save(SpecialistCreateDto dto);

    void approve(UUID id, UUID approverId, ApproverType approverType);

    void manage(UUID id, UUID ownerId);

    void recall(UUID id);

    void updateRatingById(UUID id, long rating, OperationType operationType);

    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void updateAllByTypeIdPair(Long oldTypeId, Long newTypeId);

    ShortSpecialistInfo getShortInfoById(UUID id);

    SpecialistResponseDto findById(UUID id);

    SpecialistResponseDto findByCreatorIdAndId(UUID creatorId, UUID id);

    PageResponse<SpecialistResponseDto> findAll(PageRequest page);

    PageResponse<SpecialistResponseDto> findAllByFilter(SpecialistFilter filter);

    PageResponse<SpecialistResponseDto> findAllByCreatorId(UUID creatorId, PageRequest page);

    PageResponse<SpecialistResponseDto> findAllByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter);

    void deleteById(UUID id);
}