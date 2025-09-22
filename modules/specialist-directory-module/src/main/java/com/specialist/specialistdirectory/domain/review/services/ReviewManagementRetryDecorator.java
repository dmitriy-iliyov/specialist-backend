package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.exceptions.ReviewManageException;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ReviewManagementRetryDecorator implements ReviewManagementOrchestrator {

    private final ReviewManagementOrchestrator delegate;

    public ReviewManagementRetryDecorator(@Qualifier("defaultReviewManagementOrchestrator") ReviewManagementOrchestrator delegate) {
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

    @Recover
    public ReviewResponseDto recover(OptimisticLockException ex, ReviewCreateDto dto) {
        log.error("Failed to save review after all retries: creatorId={}, specialistId={}",
                dto.getCreatorId(), dto.getSpecialistId(), ex);
        throw new ReviewManageException();
    }

    @Recover
    public ReviewResponseDto recover(Exception ex, ReviewCreateDto dto) {
        log.error("Unexpected error during review save: creatorId={}, specialistId={}",
                dto.getCreatorId(), dto.getSpecialistId(), ex);
        throw new ReviewManageException();
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

    @Recover
    public ReviewResponseDto recover(OptimisticLockException ex, ReviewUpdateDto dto) {
        log.error("Failed to update review after all retries: id={}, specialistId={}",
                dto.getId(), dto.getSpecialistId(), ex);
        throw new ReviewManageException();
    }

    @Recover
    public ReviewResponseDto recover(Exception ex, ReviewUpdateDto dto) {
        log.error("Unexpected error during review update: id={}, specialistId={}",
                dto.getId(), dto.getSpecialistId(), ex);
        throw new ReviewManageException();
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

    @Recover
    public void recover(OptimisticLockException ex, UUID creatorId, UUID specialistId, UUID id) {
        log.error("Failed to delete review after all retries: creatorId={}, specialistId={}, id={}",
                creatorId, specialistId, id, ex);
        throw new ReviewManageException();
    }

    @Recover
    public void recover(Exception ex, UUID creatorId, UUID specialistId, UUID id) {
        log.error("Unexpected error during review delete: creatorId={}, specialistId={}, id={}",
                creatorId, specialistId, id, ex);
        throw new ReviewManageException();
    }
}
