package com.specialist.schedule.work_day;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WorkDayService {

    List<String> findAvailableDayTimes(UUID specialistId, LocalDate date);

    Map<String, TimeDto> findAllDayTimes(UUID specialistId, LocalDate date);

    void deleteAllBySpecialistIdAndDate(UUID specialistId, LocalDate date);
}
