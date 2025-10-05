package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.contracts.auth.AccountDeleteEvent;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AccountDeleteTaskService {
    void save(UUID accountId, String stringRole);

    List<AccountDeleteEvent> findBatchByStatus(AccountDeleteTaskStatus status, int batchSize);

    void updateStatusBatchByIdIn(AccountDeleteTaskStatus status, Set<UUID> ids);
}
