package com.specialist.schedule.availability;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface SpecialistAvailabilityAggregator {
    Map<String, TimeDto> aggregateDay(UUID specialistId, LocalDate date);
    Map<LocalDate, Integer> aggregateMonth(UUID specialistId);
}
