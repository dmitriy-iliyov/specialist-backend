package com.specialist.specialistdirectory.domain.specialist.services.admin;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface AdminSpecialistFacade {
    SpecialistResponseDto save(UUID accountId, SpecialistCreateDto dto);

    SpecialistResponseDto findById(UUID id);

    PageResponse<?> findAll(AdminSpecialistFilter filter);

    void approve(UUID id, UUID approverId);

    SpecialistResponseDto update(SpecialistUpdateDto dto);

    void deleteById(UUID id);
}
