package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.authority.Authority;

import java.util.Set;
import java.util.UUID;

public interface SystemAccountManagementService {
    ShortAccountResponseDto takeAwayAuthoritiesById(UUID id, Set<Authority> authorities);
}
