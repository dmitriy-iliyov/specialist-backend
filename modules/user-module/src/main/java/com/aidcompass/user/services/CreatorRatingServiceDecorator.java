package com.aidcompass.user.services;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Slf4j
public class CreatorRatingServiceDecorator implements CreatorRatingService {

    private final CreatorRatingService creatorRatingService;
    private final RatingUpdateEventService eventService;


    @Autowired
    public CreatorRatingServiceDecorator(@Qualifier("unifiedUserService") CreatorRatingService creatorRatingService,
                                         RatingUpdateEventService eventService) {
        this.creatorRatingService = creatorRatingService;
        this.eventService = eventService;
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 50)
    )
    @Transactional
    @Override
    public void updateById(CreatorRatingUpdateEvent event) {
        if (eventService.existsById(event.id())) {
            return;
        }
        creatorRatingService.updateById(event);
        eventService.save(event);
    }

    @Recover
    public void recover(OptimisticLockException ole, CreatorRatingUpdateEvent event) {
        log.error("Error after all attempts to update creator rating: id={}, date={}, time={}",
                  event.creatorId(), LocalDate.now(), LocalTime.now());
        try {
            eventService.save(event);
        } catch (Exception e) {
            log.error("Error when saving event={}, date={}, time={}", event, LocalDate.now(), LocalTime.now());
        }
    }

    @Recover
    public void recover(Exception e, CreatorRatingUpdateEvent event) {
        try {
            eventService.save(event);
        } catch (Exception e1) {
            log.error("Error when saving event={}, date={}, time={}", event, LocalDate.now(), LocalTime.now());
        }
    }
}
