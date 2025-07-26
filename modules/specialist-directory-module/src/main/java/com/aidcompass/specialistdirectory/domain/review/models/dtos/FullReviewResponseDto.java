package com.aidcompass.specialistdirectory.domain.review.models.dtos;

import com.aidcompass.contracts.user.PublicUserResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.UUID;

public record FullReviewResponseDto(
        PublicUserResponseDto creator,
        ReviewResponseDto review
) { }
