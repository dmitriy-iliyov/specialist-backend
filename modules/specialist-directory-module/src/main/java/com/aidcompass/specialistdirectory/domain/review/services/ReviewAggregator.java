package com.aidcompass.specialistdirectory.domain.review.services;

import com.aidcompass.specialistdirectory.domain.review.models.dtos.FullReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.aidcompass.utils.pagination.PageResponse;

import java.util.UUID;

public interface ReviewAggregator {
    PageResponse<FullReviewResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort);
}
