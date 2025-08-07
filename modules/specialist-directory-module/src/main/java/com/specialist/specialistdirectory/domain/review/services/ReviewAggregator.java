package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.FullReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface ReviewAggregator {
    PageResponse<FullReviewResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort);
}
