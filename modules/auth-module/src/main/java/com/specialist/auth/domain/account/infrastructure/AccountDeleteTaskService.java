package com.specialist.auth.domain.account.infrastructure;

import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.contracts.auth.ImmediatelyAccountDeleteEvent;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AccountDeleteTaskService {
    void save(UUID accountId, String stringRole);

    List<ImmediatelyAccountDeleteEvent> findBatchByStatus(AccountDeleteTaskStatus status, AccountDeleteTaskStatus lockStatus, int batchSize);

    void updateStatusBatchByIdIn(AccountDeleteTaskStatus status, Set<UUID> ids);

    void deleteBatchByIds(List<UUID> ids);
}
