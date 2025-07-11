package com.aidcompass.schedule.schedule_filling_progress;

import com.aidcompass.schedule.exceptions.profile_progress.ProgressEntityNotFoundByUserIdException;
import com.aidcompass.schedule.schedule_filling_progress.models.ScheduleFilledEvent;
import com.aidcompass.schedule.schedule_filling_progress.models.ScheduleProgressEntity;
import com.aidcompass.core.security.domain.authority.models.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleProgressService {

    private final ScheduleProgressRepository repository;
    private final ApplicationEventPublisher publisher;


    @Transactional
    public void appointmentDurationFilled(UUID userId, Authority authority) {
        ScheduleProgressEntity entity;
        try {
            entity = repository.findById(userId).orElseThrow(ProgressEntityNotFoundByUserIdException::new);
            entity.setFilledAppointmentDuration(true);
        } catch (ProgressEntityNotFoundByUserIdException e) {
            entity = new ScheduleProgressEntity(userId);
            entity.setFilledAppointmentDuration(true);
        }

        boolean isComplete = entity.isFilledAppointmentDuration() && entity.isFilledFirstWorkDay();
        if (!entity.isWasComplete() && isComplete) {
            publisher.publishEvent(new ScheduleFilledEvent(userId, authority));
        }
        repository.save(entity);
    }

    @Deprecated
    @Transactional
    public void firstWorkDayFilled(UUID userId, Authority authority) {
        ScheduleProgressEntity entity;
        try {
            entity = repository.findById(userId).orElseThrow(ProgressEntityNotFoundByUserIdException::new);
            entity.setFilledFirstWorkDay(true);
        } catch (ProgressEntityNotFoundByUserIdException e) {
            entity = new ScheduleProgressEntity(userId);
            entity.setFilledFirstWorkDay(true);
        }

        boolean isComplete = entity.isFilledAppointmentDuration() && entity.isFilledFirstWorkDay();
        if (!entity.isWasComplete() && isComplete) {
            publisher.publishEvent(new ScheduleFilledEvent(userId, authority));
        }
        repository.save(entity);
    }
}
