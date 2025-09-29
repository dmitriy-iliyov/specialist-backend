package com.specialist.profile.services.rating;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.profile.models.enums.ProcessingStatus;

import java.util.Set;
import java.util.UUID;

public interface CreatorRatingEventService {
    void saveOrUpdate(ProcessingStatus status, CreatorRatingUpdateEvent event);

    boolean isProcessed(UUID id);

    Set<UUID> findBatchByStatus(ProcessingStatus status, int batchSize);

    void deleteBatchByIds(Set<UUID> ids);
}
