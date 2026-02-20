package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.filters.AdminReviewSort;
import com.specialist.specialistdirectory.exceptions.ReviewManageException;
import com.specialist.utils.pagination.PageResponse;
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
public class AdminReviewManagementRetryDecorator implements AdminReviewManagementFacade {

    private final AdminReviewManagementFacade delegate;

    public AdminReviewManagementRetryDecorator(@Qualifier("defaultAdminReviewManagementFacade") AdminReviewManagementFacade delegate) {
        this.delegate = delegate;
    }

    @Override
    public PageResponse<ReviewAggregatedResponseDto> getAll(UUID specialistId, AdminReviewSort sort) {
        return delegate.getAll(specialistId, sort);
    }

    @Override
    public void approve(UUID specialistId, UUID id) {
        delegate.approve(specialistId, id);
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 30)
    )
    @Override
    public void delete(UUID specialistId, UUID id) {
        delegate.delete(specialistId, id);
    }

    @Recover
    public ReviewResponseDto recover(OptimisticLockException ex, UUID specialistId, UUID id) {
        log.error("Failed to delete review after all retries: specialistId={}, id={}", specialistId, id, ex);
        throw new ReviewManageException();
    }

    @Recover
    public ReviewResponseDto recover(Exception ex, UUID specialistId, UUID id) {
        log.error("Unexpected error during review delete: specialistId={}, id={}", specialistId, id, ex);
        throw new ReviewManageException();
    }
}
