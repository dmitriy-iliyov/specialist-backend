package com.aidcompass.auth.domain.account.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        String email,
        @JsonProperty("created_at")
        LocalDateTime createdAt
) { }