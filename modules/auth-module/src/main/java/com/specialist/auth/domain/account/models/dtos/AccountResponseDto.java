package com.specialist.auth.domain.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        String email,
        String password,
        Role role,
        List<Authority> authorities,
        @JsonProperty("is_locked") boolean isLocked,
        @JsonProperty("lock_reason") LockReason lockReason,
        @JsonProperty("lock_term") Instant lockTerm,
        @JsonProperty("is_enable") boolean isEnable,
        @JsonProperty("disable_reason") DisableReason disableReason,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) { }
