package com.specialist.schedule.availability;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface SpecialistAvailabilityAggregator {
    Map<String, TimeDto> findDay(UUID specialistId, LocalDate date);
    Map<LocalDate, Integer> findMonth(UUID specialistId);
}
