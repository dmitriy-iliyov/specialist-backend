package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.ReviewBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.review.repositories.ReviewBufferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

// нужно отправлять те что долго висят спустя 10 мин

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewBufferServiceImpl implements ReviewBufferService, ReviewBufferUpdateService {

    @Value("${api.review-buffer.size}")
    public int REVIEW_BUFFER_SIZE;
    private final ReviewBufferRepository repository;

    @Transactional
    @Override
    public void updateByCreatorId(UUID creatorId, long rating, OperationType type) {
        ReviewBufferEntity entity = repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)
                .orElse(null);
        switch (type) {
            case PERSIST -> {
                if (entity == null) {
                    entity = new ReviewBufferEntity(creatorId, rating);
                } else {
                    long earnedSpecialistRating = entity.getSummaryRating() + rating;
                    long specialistReviewCount = entity.getReviewCount() + 1;
                    entity.setSummaryRating(earnedSpecialistRating);
                    entity.setReviewCount(specialistReviewCount);
                    if (specialistReviewCount >= REVIEW_BUFFER_SIZE) {
                        entity.setDeliveryState(DeliveryState.READY_TO_SEND);
                    }
                }
            } case UPDATE -> {
                if (entity == null) {
                    entity = new ReviewBufferEntity(creatorId, rating);
                } else {
                    long earnedSpecialistRating = entity.getSummaryRating() + rating;
                    entity.setSummaryRating(earnedSpecialistRating);
                }
            } case DELETE -> {
                if (entity == null) {
                    entity = new ReviewBufferEntity(creatorId, -rating);
                    entity.setReviewCount(-1);
                } else {
                    long earnedSpecialistRating = entity.getSummaryRating() - rating;
                    long specialistReviewCount = entity.getReviewCount() - 1;
                    entity.setSummaryRating(earnedSpecialistRating);
                    entity.setReviewCount(specialistReviewCount);
                }
            }
        }
        if (entity.getReviewCount() >= REVIEW_BUFFER_SIZE) {
            entity.setDeliveryState(DeliveryState.READY_TO_SEND);
        }
        repository.save(entity);
    }

    @Transactional
    @Override
    public void updateStateById(UUID id, DeliveryState state) {
        repository.updateDeliveryStateById(id, state);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReviewBufferEntity> findAllByDeliveryState(DeliveryState state, int batchSize) {
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
}
