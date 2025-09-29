package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.CreatorRatingBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.repositories.CreatorRatingBufferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatorRatingBufferServiceImpl implements CreatorRatingBufferService {

    @Value("${api.creator-rating-buffer.size}")
    public int RATING_BUFFER_SIZE;
    private final CreatorRatingBufferRepository repository;

    @Transactional
    @Override
    public void updateByCreatorId(UUID creatorId, long rating, OperationType type) {
        CreatorRatingBufferEntity entity = repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)
                .orElse(null);
        switch (type) {
            case PERSIST -> {
                if (entity == null) {
                    entity = new CreatorRatingBufferEntity(creatorId, rating);
                } else {
                    long earnedSpecialistRating = entity.getSummaryRating() + rating;
                    long specialistReviewCount = entity.getReviewCount() + 1;
                    entity.setSummaryRating(earnedSpecialistRating);
                    entity.setReviewCount(specialistReviewCount);
                    if (specialistReviewCount >= RATING_BUFFER_SIZE) {
                        entity.setDeliveryState(DeliveryState.READY_TO_SEND);
                    }
                }
            } case UPDATE -> {
                if (entity == null) {
                    entity = new CreatorRatingBufferEntity(creatorId, rating);
                } else {
                    long earnedSpecialistRating = entity.getSummaryRating() + rating;
                    entity.setSummaryRating(earnedSpecialistRating);
                }
            } case DELETE -> {
                if (entity == null) {
                    entity = new CreatorRatingBufferEntity(creatorId, -rating);
                    entity.setReviewCount(-1);
                } else {
                    long earnedSpecialistRating = entity.getSummaryRating() - rating;
                    long specialistReviewCount = entity.getReviewCount() - 1;
                    entity.setSummaryRating(earnedSpecialistRating);
                    entity.setReviewCount(specialistReviewCount);
                }
            }
        }
        if (entity.getReviewCount() >= RATING_BUFFER_SIZE) {
            entity.setDeliveryState(DeliveryState.READY_TO_SEND);
        }
        repository.save(entity);
    }

    @Transactional
    @Override
    public void updateDeliveryStateById(UUID id, DeliveryState state) {
        repository.updateDeliveryStateById(id, state);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CreatorRatingBufferEntity> findAllByDeliveryState(DeliveryState state, int batchSize) {
        return repository.findAllByDeliveryState(state, Pageable.ofSize(batchSize)).getContent();
    }

    @Transactional
    @Override
    public void deleteAllByIdIn(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        repository.deleteAllByIdIn(ids);
    }

    @Transactional
    @Override
    public void updateAllDeliveryStateByIdIn(Set<UUID> ids, DeliveryState state) {
        if (ids.isEmpty()) {
            return;
        }
        repository.updateAllDeliveryStatesByIdIn(ids, state);
    }

    @Transactional
    @Override
    public void updateBatchDeliveryStateByDeliveryStateAndUpdateBefore(DeliveryState oldState, DeliveryState newState,
                                                                       Instant beforeDate, Long batchSize) {
        repository.updateBatchDeliveryStateByDeliveryStateAndUpdateBefore(oldState, newState, beforeDate, batchSize);
    }
}
