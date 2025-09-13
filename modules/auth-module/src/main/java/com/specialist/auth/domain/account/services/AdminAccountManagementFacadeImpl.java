package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.TokenManager;
import com.specialist.auth.domain.account.models.dtos.DemodeRequest;
import com.specialist.auth.domain.account.models.dtos.DisableRequest;
import com.specialist.auth.domain.account.models.dtos.LockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminAccountFacadeImpl implements AdminAccountFacade {

    private final AdminAccountService accountService;
    private final TokenManager tokenManager;

    @Transactional
    @Override
    public void disableById(UUID id, DisableRequest request) {
        accountService.disableById(id, request);
        tokenManager.revokeAll(id);
    }

    @Transactional
    @Override
    public void lockById(UUID id, LockRequest request) {
        accountService.lockById(id, request);
        tokenManager.revokeAll(id);
    }

    @Transactional
    @Override
    public void demoteById(DemodeRequest request) {
        accountService.takeAwayAuthoritiesById(request.getAccountId(), request.getAuthorities());
        tokenManager.revokeAll(request.getAccountId());
    }
}
