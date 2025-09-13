package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.dtos.DisableRequest;
import com.specialist.auth.domain.account.models.dtos.LockRequest;
import com.specialist.auth.domain.authority.Authority;

import java.util.Set;
import java.util.UUID;

public interface AdminAccountService {
    void takeAwayAuthoritiesById(UUID id, Set<Authority> authorities);

    void lockById(UUID id, LockRequest request);

    void unlockById(UUID id);

    void disableById(UUID id, DisableRequest request);

    void enableById(UUID id);
}
