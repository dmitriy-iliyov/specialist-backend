package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.dtos.DisableRequest;
import com.specialist.auth.domain.account.models.dtos.LockRequest;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.exceptions.AccountNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminAccountManagementServiceImpl implements AdminAccountManagementService {

    private final AccountRepository repository;

    @Transactional
    @Override
    public void takeAwayAuthoritiesById(UUID id, Set<Authority> authorities) {
        AccountEntity accountEntity = repository.findById(id).orElseThrow(AccountNotFoundByIdException::new);
        accountEntity.getAuthorities().removeIf(entity -> authorities.contains(entity.getAuthorityAsEnum()));
        repository.save(accountEntity);
    }

    @Transactional
    @Override
    public void lockById(UUID id, LockRequest request) {
        repository.lockById(id, request.reason(), request.term().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Transactional
    @Override
    public void unlockById(UUID id) {
        repository.unlockById(id);
    }

    @Transactional
    @Override
    public void disableById(UUID id, DisableRequest request) {
        repository.disableById(id, request.reason());
    }

    @Transactional
    @Override
    public void enableById(UUID id) {
        repository.enableById(id);
    }
}
