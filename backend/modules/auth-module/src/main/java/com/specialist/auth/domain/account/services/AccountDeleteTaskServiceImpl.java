package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.AccountDeleteTaskEntity;
import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.auth.domain.account.repositories.AccountDeleteTaskRepository;
import com.specialist.contracts.auth.AccountDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountDeleteTaskServiceImpl implements AccountDeleteTaskService {

    private final AccountDeleteTaskRepository repository;

    @Transactional
    @Override
    public void save(UUID accountId, String stringRole) {
        repository.save(new AccountDeleteTaskEntity(accountId, stringRole));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AccountDeleteEvent> findBatchByStatus(AccountDeleteTaskStatus status, int batchSize) {
        return repository.findBatchByStatus(status, Pageable.ofSize(batchSize))
                .stream()
                .map(entity -> new AccountDeleteEvent(entity.getId(), entity.getAccountId(), entity.getStringRole()))
                .toList();

    }

    @Transactional
    @Override
    public void updateStatusBatchByIdIn(AccountDeleteTaskStatus status, Set<UUID> ids) {
        repository.updateBatchStatusByIdIn(status, ids);
    }
}
