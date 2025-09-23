package com.specialist.schedule.interval.services;

import com.specialist.schedule.appointment.models.marker.AppointmentMarker;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.models.dto.SystemIntervalCreatedDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IntervalService {
    IntervalResponseDto save(UUID specialistId, SystemIntervalCreatedDto dto);

    Set<IntervalResponseDto> cut(AppointmentMarker dto, Long id);

    List<IntervalResponseDto> findAllBySpecialistIdAndDate(UUID specialistId, LocalDate date);

    List<LocalDate> findMonthDatesBySpecialistId(UUID specialistId, LocalDate start, LocalDate end);

    List<IntervalResponseDto> findAllNearestBySpecialistIdIn(Set<UUID> specialistIds);

    IntervalResponseDto findBySpecialistIdAndStartAndDate(UUID specialistId, LocalDate date, LocalTime start);

    IntervalResponseDto findById(Long id);

    IntervalResponseDto findNearestBySpecialistId(UUID specialistId);

    IntervalResponseDto deleteBySpecialistIdAndId(UUID specialistId, Long id);

    void deleteAllBySpecialistIdAndDate(UUID specialistId, LocalDate date);
}
