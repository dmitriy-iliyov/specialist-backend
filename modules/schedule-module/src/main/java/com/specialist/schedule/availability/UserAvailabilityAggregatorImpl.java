package com.specialist.schedule.availability;

import com.specialist.schedule.appointment_duration.AppointmentDurationService;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAvailabilityAggregatorImpl implements UserAvailabilityAggregator {

    private final IntervalService intervalService;
    private final AppointmentDurationService appointmentDurationService;

    @Transactional(readOnly = true)
    @Override
    public List<String> findDay(UUID specialistId, LocalDate date) {
        Long duration = appointmentDurationService.findBySpecialistId(specialistId);
        List<IntervalResponseDto> dtoList = intervalService.findAllBySpecialistIdAndDate(specialistId, date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<LocalTime> times = new ArrayList<>();
        for (IntervalResponseDto dto: dtoList) {
            LocalTime time = dto.start();
            long intervalDuration = Duration.between(time, dto.end()).toMinutes();
            for (int i = 0; i < intervalDuration/duration; i++) {
                times.add(time);
                time = time.plusMinutes(duration);
            }
        }
        return times.stream()
                .map(time -> time.format(formatter))
                .toList();    }

    @Override
    public List<LocalDate> findMonth(UUID specialistId) {
        LocalDate currentDate = LocalDate.now();
        LocalDate end = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(27);
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(LocalTime.of(20, 0))) {
            currentDate = currentDate.plusDays(2);
        } else {
            currentDate = currentDate.plusDays(1);
        }
        return intervalService.findMonthDatesBySpecialistId(specialistId, currentDate, end);
    }
}
