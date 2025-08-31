package com.specialist.specialistdirectory.domain.review.models.dtos;

import com.specialist.contracts.user.PublicUserResponseDto;

public record ReviewAggregatedResponseDto(
        PublicUserResponseDto user,
        ReviewResponseDto review
) { }
