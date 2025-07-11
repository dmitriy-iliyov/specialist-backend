package com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TypeDto(
        Long id,

        String title,

        @JsonProperty("is_approved")
        boolean isApproved,

        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime updatedAt
) { }