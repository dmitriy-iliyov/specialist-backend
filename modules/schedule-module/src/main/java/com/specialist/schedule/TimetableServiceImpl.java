package com.specialist.schedule;

import com.specialist.schedule.appointment.services.AppointmentService;
import com.specialist.schedule.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimetableServiceImpl implements TimetableService {

    private final IntervalService intervalService;
    private final AppointmentService appointmentOrchestrator;

    @Override
    public List<LocalDate> findAvailableMonthDates(UUID volunteerId) {
        LocalDate currentDate = LocalDate.now();
        LocalDate end = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(27);
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(LocalTime.of(20, 0))) {
            currentDate = currentDate.plusDays(2);
        } else {
            currentDate = currentDate.plusDays(1);
        }
        return intervalService.findMonthDatesBySpecialistId(volunteerId, currentDate, end);
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public Map<LocalDate, Integer> findAllMonthDates(UUID volunteerId) {
        LocalDate start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(27);
        Map<LocalDate, Integer> monthDates = new LinkedHashMap<>();
        List<LocalDate> intervals = intervalService.findMonthDatesBySpecialistId(volunteerId, start, end);
        List<LocalDate> appointmentDates = appointmentOrchestrator.findMonthDatesBySpecialistId(volunteerId, start, end);
        for (int i = 0; i < 28; i++) {
            LocalDate iDate = start.plusDays(i);
            if (appointmentDates.contains(iDate)) {
                monthDates.put(iDate, 2);
            } else if (intervals.contains(iDate)) {
                monthDates.put(iDate, 1);
            } else {
                monthDates.put(iDate, 0);
            }
        }
        return monthDates.keySet().stream().sorted()
                .collect(Collectors.toMap(
                        Function.identity(),
                        monthDates::get,
                        (k1, k2) -> k2,
                        LinkedHashMap::new)
                );
    }
}