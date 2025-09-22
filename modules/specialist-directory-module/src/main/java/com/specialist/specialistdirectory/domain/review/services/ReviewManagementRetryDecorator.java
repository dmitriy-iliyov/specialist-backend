package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ReviewOrchestratorRetryDecorator implements ReviewOrchestrator {

    private final ReviewOrchestrator delegate;

    public ReviewOrchestratorRetryDecorator(@Qualifier("defaultReviewOrchestrator") ReviewOrchestrator delegate) {
        this.delegate = delegate;
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 30)
    )
    @Override
    public ReviewResponseDto save(ReviewCreateDto dto) {
        return delegate.save(dto);
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 30)
    )
    @Override
    public ReviewResponseDto update(ReviewUpdateDto dto) {
        return delegate.update(dto);
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 30)
    )
    @Override
    public void delete(UUID creatorId, UUID specialistId, UUID id) {
        delegate.delete(creatorId, specialistId, id);
    }

    // Общий recover для save (Spring найдет по сигнатуре)
    @Recover
    public ReviewResponseDto recover(OptimisticLockException ex, ReviewCreateDto dto) {
        log.error("Failed to save review after all retries: creatorId={}, specialistId={}",
                dto.getCreatorId(), dto.getSpecialistId(), ex);
        throw new ReviewOperationException("Failed to save review after retries", ex);
    }

    @Recover
    public ReviewResponseDto recover(Exception ex, ReviewCreateDto dto) {
        log.error("Unexpected error during review save: creatorId={}, specialistId={}",
                dto.getCreatorId(), dto.getSpecialistId(), ex);
        throw new ReviewOperationException("Unexpected error during review save", ex);
    }

    // Общий recover для update
    @Recover
    public ReviewResponseDto recover(OptimisticLockException ex, ReviewUpdateDto dto) {
        log.error("Failed to update review after all retries: id={}, specialistId={}",
                dto.getId(), dto.getSpecialistId(), ex);
        throw new ReviewOperationException("Failed to update review after retries", ex);
    }

    // Общий recover для delete
    @Recover
    public void recover(OptimisticLockException ex, UUID creatorId, UUID specialistId, UUID id) {
        log.error("Failed to delete review after all retries: creatorId={}, specialistId={}, id={}",
                creatorId, specialistId, id, ex);
        throw new ReviewOperationException("Failed to delete review after retries", ex);
    }

    @Recover
    public ReviewResponseDto recover(Exception ex, ReviewUpdateDto dto) {
        log.error("Unexpected error during review update: id={}, specialistId={}",
                dto.getId(), dto.getSpecialistId(), ex);
        throw new ReviewOperationException("Unexpected error during review update", ex);
    }

    @Recover
    public void recover(Exception ex, UUID creatorId, UUID specialistId, UUID id) {
        log.error("Unexpected error during review delete: creatorId={}, specialistId={}, id={}",
                creatorId, specialistId, id, ex);
        throw new ReviewOperationException("Unexpected error during review delete", ex);
    }
}
