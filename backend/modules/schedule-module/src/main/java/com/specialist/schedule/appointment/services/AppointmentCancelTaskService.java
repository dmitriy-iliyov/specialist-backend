package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskResponseDto;
import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;

import java.time.LocalDate;
import java.util.UUID;

public interface AppointmentCancelTaskService {
    void save(AppointmentCancelTaskCreateDto dto);

    Boolean existsByParticipantIdAndTypeAndDate(UUID participantId, AppointmentCancelTaskType type, LocalDate date);

    void deleteById(UUID id);

    AppointmentCancelTaskResponseDto findFirstByType(AppointmentCancelTaskType type);
}
