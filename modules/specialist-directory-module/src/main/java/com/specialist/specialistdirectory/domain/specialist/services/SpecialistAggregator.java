package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.FullSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

public interface SpecialistAggregator {
    PageResponse<FullSpecialistResponseDto> findAll(PageRequest page);

    PageResponse<FullSpecialistResponseDto> findAllByFilter(SpecialistFilter filter);
}
