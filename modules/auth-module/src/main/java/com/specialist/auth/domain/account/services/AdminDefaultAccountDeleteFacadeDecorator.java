package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.TokenManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DefaultAccountDeleteFacadeAdminDecorator implements AccountDeleteFacade {

    private final AccountDeleteFacade delegate;
    private final TokenManager tokenManager;

    public DefaultAccountDeleteFacadeAdminDecorator(@Qualifier("defaultAccountDeleteFacade") AccountDeleteFacade delegate,
                                                    TokenManager tokenManager) {
        this.delegate = delegate;
        this.tokenManager = tokenManager;
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        delegate.delete(id);
        tokenManager.revokeAll(id);
    }
}
