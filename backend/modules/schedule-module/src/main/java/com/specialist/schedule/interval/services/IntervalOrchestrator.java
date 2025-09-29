package com.specialist.schedule.interval.services;

import com.specialist.schedule.appointment.models.marker.AppointmentMarker;
import com.specialist.schedule.interval.models.dto.IntervalCreateDto;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.models.dto.SystemIntervalCreatedDto;

import java.util.UUID;

public interface IntervalOrchestrator {
    IntervalResponseDto save(UUID specialistId, IntervalCreateDto inputDto);

    void systemSave(UUID ownerId, SystemIntervalCreatedDto dto);

    void cut(AppointmentMarker dto, Long id);

    void delete(UUID specialistId, Long id);
}
