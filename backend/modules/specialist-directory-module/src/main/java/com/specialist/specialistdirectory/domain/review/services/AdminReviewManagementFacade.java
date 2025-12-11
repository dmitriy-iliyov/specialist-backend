package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.review.models.filters.AdminReviewSort;
import com.specialist.utils.pagination.PageResponse;

import java.util.UUID;

public interface AdminReviewManagementFacade {
    void approve(UUID specialistId, UUID id);

    void delete(UUID specialistId, UUID id);

    PageResponse<ReviewAggregatedResponseDto> getAll(UUID uuid, AdminReviewSort sort);
}
