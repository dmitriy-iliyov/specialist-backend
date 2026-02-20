package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewDeleteRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateRequest;
import com.specialist.specialistdirectory.exceptions.ReviewManageException;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReviewManagementRetryDecorator implements ReviewManagementFacade {

    private final ReviewManagementFacade delegate;

    public ReviewManagementRetryDecorator(@Qualifier("defaultReviewManagementFacade") ReviewManagementFacade delegate) {
        this.delegate = delegate;
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 30)
    )
    @Override
    public ReviewResponseDto save(ReviewCreateRequest request) {
        return delegate.save(request);
    }

    @Recover
    public ReviewResponseDto recover(OptimisticLockException ex, ReviewCreateRequest request) {
        log.error("Failed to save review after all retries: creatorId={}, specialistId={}",
                request.creatorId(), request.specialistId(), ex);
        throw new ReviewManageException();
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 30)
    )
    @Override
    public ReviewResponseDto update(ReviewUpdateRequest request) {
        return delegate.update(request);
    }

    @Recover
    public ReviewResponseDto recover(OptimisticLockException ex, ReviewUpdateRequest request) {
        log.error("Failed to update review after all retries: id={}, specialistId={}",
                request.id(), request.specialistId(), ex);
        throw new ReviewManageException();
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 30)
    )
    @Override
    public void delete(ReviewDeleteRequest request) {
        delegate.delete(request);
    }

    @Recover
    public void recover(OptimisticLockException ex, ReviewDeleteRequest request) {
        log.error("Failed to delete review after all retries: creatorId={}, specialistId={}, id={}",
                request.creatorId(), request.specialistId(), request.id(), ex);
        throw new ReviewManageException();
    }
}
