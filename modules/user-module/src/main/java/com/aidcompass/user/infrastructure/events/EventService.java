package com.aidcompass.user.infrastructure.events;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;
import com.aidcompass.user.models.enums.ProcessingStatus;

import java.util.Set;
import java.util.UUID;

public interface EventService {
    void saveOrUpdate(ProcessingStatus status, CreatorRatingUpdateEvent event);

    boolean isProcessed(UUID id);

    Set<UUID> findBatchByStatus(ProcessingStatus status, int batchSize);

    void deleteBatchByIds(Set<UUID> ids);
}
