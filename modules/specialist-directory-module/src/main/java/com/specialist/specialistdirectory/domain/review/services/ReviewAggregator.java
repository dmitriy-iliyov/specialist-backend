package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface ReviewAggregator {
    PageResponse<ReviewAggregatedResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort);
}
