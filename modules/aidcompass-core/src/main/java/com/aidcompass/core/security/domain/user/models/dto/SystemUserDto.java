package com.aidcompass.core.security.domain.user.models.dto;


import com.aidcompass.core.security.domain.authority.models.Authority;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


public record SystemUserDto(
        UUID id,
        Long emailId,
        String email,
        String password,
        List<Authority> authorities,
        Instant createdAt,
        Instant updatedAt
) { }
