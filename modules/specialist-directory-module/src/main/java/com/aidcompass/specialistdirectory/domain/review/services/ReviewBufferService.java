package com.aidcompass.specialistdirectory.domain.review.services;

import java.util.Set;
import java.util.UUID;

public interface ReviewBufferService {
    void put(UUID creatorId, long rating);

    void markAsSent(UUID id);

    void sendEventsBatch();

    void popAllByIdIn(Set<UUID> ids);

    void markBatchAsReadyToSend(Set<UUID> ids);
}
