package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
@Slf4j
public class DefaultSpecialistRatingServiceDecorator implements SpecialistRatingService {

    private final SpecialistRatingService service;

    public DefaultSpecialistRatingServiceDecorator(@Qualifier("defaultSpecialistRatingService")
                                                   SpecialistRatingService service) {
        this.service = service;
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 50)
    )
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateRatingById(UUID id, long earnedRating, OperationType operationType) {
        service.updateRatingById(id, earnedRating, operationType);
    }

    @Recover
    public void recover(OptimisticLockException e, UUID id, long rating, OperationType operation) {
        log.error("Error when reviewing specialist: id={}, date={}, time={}", id, LocalDate.now(), LocalTime.now());
    }
}
