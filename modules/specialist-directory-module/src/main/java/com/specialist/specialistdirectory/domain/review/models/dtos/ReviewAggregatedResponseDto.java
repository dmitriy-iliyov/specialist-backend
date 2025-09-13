package com.specialist.specialistdirectory.domain.review.models.dtos;

import com.specialist.contracts.user.dto.PublicUserResponseDto;

public record ReviewAggregatedResponseDto(
        PublicUserResponseDto user,
        ReviewResponseDto review
) { }
