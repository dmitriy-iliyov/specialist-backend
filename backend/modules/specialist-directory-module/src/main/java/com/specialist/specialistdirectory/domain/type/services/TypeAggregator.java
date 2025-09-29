package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.models.dtos.FullTypeResponseDto;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;

public interface TypeAggregator {
    PageResponse<FullTypeResponseDto> findAll(PageRequest page);
    PageResponse<FullTypeResponseDto> findAllUnapproved(PageRequest page);
}
