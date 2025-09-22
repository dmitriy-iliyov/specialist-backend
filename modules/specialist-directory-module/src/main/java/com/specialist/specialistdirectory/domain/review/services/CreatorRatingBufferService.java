package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.ReviewBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ReviewBufferService {
    void updateStateById(UUID id, DeliveryState state);

    List<ReviewBufferEntity> findAllByDeliveryState(DeliveryState state, int batchSize);

    void deleteAllByIdIn(Set<UUID> ids);

    void updateAllDeliveryStateByIdIn(Set<UUID> ids, DeliveryState state);
}
