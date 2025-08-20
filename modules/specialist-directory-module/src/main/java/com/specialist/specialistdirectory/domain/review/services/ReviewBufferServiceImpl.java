package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.ReviewBufferEntity;
import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewBufferServiceImpl implements ReviewBufferService {

    @Value("${api.review-buffer.size}")
    public int REVIEW_BUFFER_SIZE;
    private final ReviewBufferRepository repository;

    @Transactional
    @Override
    public void put(UUID creatorId, long rating) {
        ReviewBufferEntity entity = repository.findByCreatorIdAndDeliveryState(creatorId, DeliveryState.PREPARE)
                .orElse(null);
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
        repository.save(entity);
    }

    @Transactional
    @Override
    public void markAsSent(UUID id) {
        repository.updateDeliveryStateById(DeliveryState.SENT, id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReviewBufferEntity> findBatchByDeliveryState(DeliveryState state, int batchSize) {
        return repository.findAllByDeliveryState(state, Pageable.ofSize(batchSize)).getContent();
    }

    @Transactional
    @Override
    public void popAllByIdIn(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        repository.deleteAllByIdIn(ids);
    }

    @Transactional
    @Override
    public void markBatchAsReadyToSend(Set<UUID> ids) {
        if (ids.isEmpty()) {
            return;
        }
        repository.updateAllDeliveryStatesByIdIn(DeliveryState.READY_TO_SEND, ids);
    }
}
