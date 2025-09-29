package com.specialist.specialistdirectory.domain.specialist.services.admin;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.AdminSpecialistAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;

public interface AdminSpecialistAggregator {
    PageResponse<AdminSpecialistAggregatedResponseDto> aggregate(AdminSpecialistFilter filter);
}
