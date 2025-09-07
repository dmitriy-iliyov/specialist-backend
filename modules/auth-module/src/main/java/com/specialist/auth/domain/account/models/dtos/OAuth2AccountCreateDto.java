package com.specialist.auth.domain.account.models.dtos;

import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;

import java.util.List;

public record OAuth2AccountCreateDto(
        String email,
        Provider provider,
        Role role,
        List<Authority>authorities
) { }