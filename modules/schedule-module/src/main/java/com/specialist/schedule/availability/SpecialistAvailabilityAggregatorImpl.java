package com.specialist.schedule.availability;

import com.specialist.schedule.appointment.infrastructure.AppointmentService;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentStatus;
import com.specialist.schedule.appointment_duration.AppointmentDurationService;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialistAvailabilityAggregatorImpl implements SpecialistAvailabilityAggregator {

    private final IntervalService intervalService;
    private final AppointmentService appointmentService;
    private final AppointmentDurationService appointmentDurationService;

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public Map<String, TimeDto> aggregateDay(UUID specialistId, LocalDate date) {
        List<IntervalResponseDto> intervals = intervalService.findAllBySpecialistIdAndDate(specialistId, date);
        List<AppointmentResponseDto> appointments = appointmentService.findAllBySpecialistIdAndDateAndStatus(
                specialistId, date, AppointmentStatus.SCHEDULED
        );
        Map<LocalTime, Pair<LocalTime, TimeDto>> existsTimeMap = appointments.stream()
                .collect(Collectors.toMap(
                        AppointmentResponseDto::start,
                        appointment -> Pair.of(appointment.end(), new TimeDto(appointment.id(), 2)))
                );
        intervals.forEach(interval -> existsTimeMap.put(
                interval.start(),
                Pair.of(interval.end(), new TimeDto(interval.id(), 1)))
        );
        List<LocalTime> times = existsTimeMap.keySet().stream().sorted().toList();
        Long duration = appointmentDurationService.findBySpecialistId(specialistId);
        Map<LocalTime, TimeDto> resultMap = new LinkedHashMap<>();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = start.plusMinutes(duration);
        int i = 0;
        while(end.getHour() <= 20) {
            if (end.getHour() == 20 && end.getMinute() != 0) {
                break;
            }
            if (i < times.size()) {
                LocalTime eTime = times.get(i);
                if (end.isBefore(eTime)) {
                    resultMap.put(start, new TimeDto(null, 0));
                    start = end;
                } else if (end.equals(eTime)) {
                    resultMap.put(start, new TimeDto(null, 0));
                    resultMap.put(eTime, existsTimeMap.get(eTime).getRight());
                    start = existsTimeMap.get(eTime).getLeft();
                    i++;
                } else {
                    resultMap.put(eTime, existsTimeMap.get(eTime).getRight());
                    start = existsTimeMap.get(eTime).getLeft();
                    i++;
                }
            } else {
                resultMap.put(start, new TimeDto(null, 0));
                start = end;
            }
            end = start.plusMinutes(duration);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return resultMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        entry -> entry.getKey().format(formatter),
                        Map.Entry::getValue,
                        (e1, e2) -> e2,
                        LinkedHashMap::new
                ));
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public Map<LocalDate, Integer> aggregateMonth(UUID specialistId) {
        LocalDate start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate end = start.plusDays(27);
        Map<LocalDate, Integer> monthDates = new LinkedHashMap<>();
        List<LocalDate> intervals = intervalService.findBySpecialistIdAndDateInterval(specialistId, start, end);
        List<LocalDate> appointmentDates = appointmentService.findBySpecialistIdAndDateInterval(specialistId, start, end);
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
