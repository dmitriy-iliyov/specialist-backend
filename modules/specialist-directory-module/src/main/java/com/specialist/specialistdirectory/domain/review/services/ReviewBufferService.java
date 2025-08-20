package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.ReviewBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ReviewBufferService {
    void put(UUID creatorId, long rating);

    void markAsSent(UUID id);

    List<ReviewBufferEntity> findBatchByDeliveryState(DeliveryState state, int batchSize);

    void popAllByIdIn(Set<UUID> ids);

    void markBatchAsReadyToSend(Set<UUID> ids);
}
