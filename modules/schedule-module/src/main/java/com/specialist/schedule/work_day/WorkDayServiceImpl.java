package com.specialist.schedule.work_day;

import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentStatus;
import com.specialist.schedule.appointment.services.AppointmentService;
import com.specialist.schedule.appointment_duration.AppointmentDurationService;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkDayServiceImpl implements WorkDayService {

    private final IntervalService intervalService;
    private final AppointmentService appointmentService;
    private final AppointmentDurationService appointmentDurationService;

    @Transactional(readOnly = true)
    @Override
    public List<String> findAvailableDayTimes(UUID specialistId, LocalDate date) {
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
                .toList();
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    @Override
    public Map<String, TimeDto> findAllDayTimes(UUID specialistId, LocalDate date) {
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

    @Transactional
    @Override
    public void deleteAllBySpecialistIdAndDate(UUID specialistId, LocalDate date) {
        intervalService.deleteAllBySpecialistIdAndDate(specialistId, date);
        appointmentService.cancelAllByDate(specialistId, date);
    }
}