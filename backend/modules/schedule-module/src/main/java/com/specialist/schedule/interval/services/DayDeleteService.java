package com.specialist.schedule.interval.services;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

public interface DayDeleteService {
    @Transactional
    void deleteAllBySpecialistIdAndDate(UUID specialistId, LocalDate date);
}
