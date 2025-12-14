package com.specialist.specialistdirectory.domain.review.infrastructure;

import com.specialist.specialistdirectory.domain.review.models.CreatorRatingBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.specialist.specialistdirectory.domain.review.services.CreatorRatingUpdateService;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CreatorRatingBufferService extends CreatorRatingUpdateService {
    void updateDeliveryStateById(UUID id, DeliveryState state);

    List<CreatorRatingBufferEntity> findAllByDeliveryState(DeliveryState state, int batchSize);

    void deleteAllByIdIn(Set<UUID> ids);

    void updateAllDeliveryStateByIdIn(Set<UUID> ids, DeliveryState state);

    void updateBatchDeliveryStateByDeliveryStateAndUpdateBefore(DeliveryState oldState, DeliveryState newState,
                                                                Instant beforeDate, Long batchSize);
}
