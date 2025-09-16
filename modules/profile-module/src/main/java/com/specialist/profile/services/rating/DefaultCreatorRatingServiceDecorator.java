package com.specialist.profile.services.rating;

import com.specialist.contracts.profile.CreatorRatingUpdateEvent;
import com.specialist.profile.infrastructure.events.EventService;
import com.specialist.profile.models.enums.ProcessingStatus;
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
public class DefaultCreatorRatingServiceDecorator implements CreatorRatingService {

    private final CreatorRatingService creatorRatingService;
    private final EventService eventService;

    @Autowired
    public DefaultCreatorRatingServiceDecorator(@Qualifier("defaultCreatorRatingService") CreatorRatingService creatorRatingService,
                                                EventService eventService) {
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
        if (eventService.isProcessed(event.id())) {
            return;
        }
        creatorRatingService.updateById(event);
        eventService.saveOrUpdate(ProcessingStatus.PROCESSED, event);
    }

    @Recover
    public void recover(OptimisticLockException ole, CreatorRatingUpdateEvent event) {
        log.error("Error after all attempts to update creator rating: id={}, date={}, time={}",
                  event.creatorId(), LocalDate.now(), LocalTime.now());
    }

    @Recover
    public void recover(Exception e, CreatorRatingUpdateEvent event) {
        try {
            eventService.saveOrUpdate(ProcessingStatus.FAILED, event);
        } catch (Exception e1) {
            log.error("Error when saving event={}, date={}, time={}", event, LocalDate.now(), LocalTime.now());
        }
    }
}
