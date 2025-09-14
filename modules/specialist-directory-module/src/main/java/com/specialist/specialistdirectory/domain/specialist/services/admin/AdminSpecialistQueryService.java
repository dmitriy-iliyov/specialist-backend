package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.FullSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;

public interface AdminSpecialistQueryService {
    PageResponse<FullSpecialistResponseDto> findAll(AdminSpecialistFilter filter);
}