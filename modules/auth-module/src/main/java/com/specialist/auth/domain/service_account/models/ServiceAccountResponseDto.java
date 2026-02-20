package com.specialist.auth.domain.service_account.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ServiceAccountResponseDto(
        UUID id,

        String name,

        Role role,

        List<Authority> authorities,

        @JsonProperty("creator_id")
        UUID creatorId,

        @JsonProperty("updater_id")
        UUID updaterId,

        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy:MM:dd mm:HH")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        @JsonFormat(pattern = "yyyy:MM:dd mm:HH")
        LocalDateTime updatedAt
) { }
