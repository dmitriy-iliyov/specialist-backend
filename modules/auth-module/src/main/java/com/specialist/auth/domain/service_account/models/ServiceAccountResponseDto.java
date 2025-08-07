package com.specialist.auth.domain.service_account.models;

import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ServiceAccountResponseDto(
        UUID id,
        Role role,
        List<Authority> authorities,
        UUID creatorId,
        UUID updaterId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { }
