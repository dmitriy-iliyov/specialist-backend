package com.aidcompass.specialistdirectory.domain.review.services.interfases;

import com.aidcompass.specialistdirectory.domain.review.models.dtos.FullReviewResponseDto;
import com.aidcompass.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;

import java.util.UUID;

public interface ReviewAggregator {
    PageResponse<FullReviewResponseDto> findAllWithSortBySpecialistId(UUID specialistId, ReviewSort sort);
}
