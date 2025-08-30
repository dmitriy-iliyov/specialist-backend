package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.authority.Authority;
import com.specialist.contracts.auth.AccountAuthorityFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountAuthorityFacadeImpl implements AccountAuthorityFacade {

    private final AccountService service;

    @Override
    public void postUserCreateDemand(UUID accountId) {
        service.takeAwayAuthoritiesById(accountId, Set.of(Authority.REGISTRATION));
    }
}
