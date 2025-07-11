package com.aidcompass.specialistdirectory.domain.review.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record FullReviewResponseDto(
        @JsonUnwrapped
        ReviewResponseDto review,

        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("full_name")
        String fullName
) { }
