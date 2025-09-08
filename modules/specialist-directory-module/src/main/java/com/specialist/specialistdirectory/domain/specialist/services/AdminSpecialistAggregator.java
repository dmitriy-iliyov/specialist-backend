package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;

public interface AdminSpecialistAggregator {
    PageResponse<SpecialistAggregatedResponseDto> aggregate(AdminSpecialistFilter filter);
}
