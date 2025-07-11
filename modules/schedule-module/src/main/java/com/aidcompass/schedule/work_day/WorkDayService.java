package com.aidcompass.schedule.work_day;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface WorkDayService {

    List<String> findAvailableDayTimes(UUID ownerId, LocalDate date);

    Map<String, TimeDto> findAllDayTimes(UUID ownerId, LocalDate date);

    void deleteAllByVolunteerIdAndDate(UUID ownerId, LocalDate date);
}
