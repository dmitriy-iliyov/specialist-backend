package com.aidcompass.specialistdirectory.domain.review.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.UUID;

public record FullReviewResponseDto(
        @JsonProperty("creator_id")
        UUID creatorId,

        @JsonProperty("creator_name")
        String creatorName,

        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonUnwrapped
        ReviewResponseDto review
) { }
