package com.specialist.auth.domain.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record ShortAccountResponseDto(
        UUID id,
        String email,
        @JsonProperty("created_at")
        LocalDateTime createdAt
) { }