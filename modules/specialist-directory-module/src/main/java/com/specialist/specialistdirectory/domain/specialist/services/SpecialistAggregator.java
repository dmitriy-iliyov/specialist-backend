package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

public interface SpecialistAggregator {
    PageResponse<SpecialistAggregatedResponseDto> findAll(PageDataHolder page);

    PageResponse<SpecialistAggregatedResponseDto> findAllByFilter(SpecialistFilter filter);
}
