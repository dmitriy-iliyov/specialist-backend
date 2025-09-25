package com.specialist.schedule.availability;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UserAvailabilityAggregator {
    List<String> aggregateDay(UUID specialistId, LocalDate date);
    List<LocalDate> aggregateMonth(UUID specialistId);
}
