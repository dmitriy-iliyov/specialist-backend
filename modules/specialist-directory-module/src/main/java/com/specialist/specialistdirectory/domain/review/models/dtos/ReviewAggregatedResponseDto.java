package com.specialist.specialistdirectory.domain.review.models.dtos;

import com.specialist.contracts.user.dto.UnifiedProfileResponseDto;

public record ReviewAggregatedResponseDto(
        UnifiedProfileResponseDto user,
        ReviewResponseDto review
) { }
