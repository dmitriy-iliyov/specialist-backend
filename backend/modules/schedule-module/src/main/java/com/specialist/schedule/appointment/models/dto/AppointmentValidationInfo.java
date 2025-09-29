package com.specialist.schedule.appointment.models.dto;

import com.specialist.schedule.appointment.models.enums.ValidationStatus;
import com.specialist.schedule.appointment.models.marker.AppointmentMarker;

import java.util.UUID;

public record AppointmentValidationInfo(
        ValidationStatus status,
        UUID userId,
        AppointmentMarker dto,
        Long intervalId
) { }