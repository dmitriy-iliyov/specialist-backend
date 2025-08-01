package com.aidcompass.auth.domain.account.models.dtos;

import com.aidcompass.auth.domain.account.models.enums.LockReasonType;
import com.aidcompass.auth.domain.account.models.enums.UnableReasonType;
import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.role.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

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
        @JsonProperty("lock_reason") LockReasonType lockReason,
        @JsonProperty("lock_term") Instant lockTerm,
        @JsonProperty("is_enable") boolean isEnable,
        @JsonProperty("unable_reason") UnableReasonType unableReason,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) { }
