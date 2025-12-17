package com.specialist.specialistdirectory.domain.review.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponseDto(
        UUID id,

        @JsonProperty("creator_id")
        UUID creatorId,

        String description,

        String reviewUrl,

        Integer rating,

        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt
) { }
