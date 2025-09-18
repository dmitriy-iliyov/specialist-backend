package com.specialist.schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TimetableService {
    List<LocalDate> findAvailableMonthDates(UUID specialistId);
    Map<LocalDate, Integer> findAllMonthDates(UUID volunteerId);
}
