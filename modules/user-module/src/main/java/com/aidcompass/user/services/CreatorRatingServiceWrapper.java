package com.aidcompass.user.services;

import com.aidcompass.contracts.user.CreatorRatingUpdateEvent;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.sound.midi.Track;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Slf4j
public class CreatorRatingServiceWrapper implements CreatorRatingService {

    private final CreatorRatingService service;

    @Autowired
    public CreatorRatingServiceWrapper(@Qualifier("unifiedUserService") CreatorRatingService service) {
        this.service = service;
    }

    @Retryable(
            retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 50)
    )
    @KafkaListener(topics = {"${api.kafka.topic.review-buffer}"})
    @Override
    public void updateById(CreatorRatingUpdateEvent event) {
        service.updateById(event);
    }

    @Recover
    public void recover(OptimisticLockException e, CreatorRatingUpdateEvent event) {
        log.error("Error after all attempts to update creator rating: id={}, date={}, time={}",
                  event.creatorId(), LocalDate.now(), LocalTime.now());
    }
}
