package com.specialist.schedule.appointment.models.dto;

import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;

import java.time.LocalDate;
import java.util.UUID;

public record AppointmentCancelTaskDto(
        UUID participantId,
        AppointmentCancelTaskType type,
        LocalDate date
) { }
