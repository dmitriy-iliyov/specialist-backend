package com.aidcompass.schedule.appointment.models.dto;

import com.aidcompass.schedule.appointment.models.enums.ValidationStatus;
import com.aidcompass.schedule.appointment.models.marker.AppointmentMarker;

import java.util.UUID;

public record AppointmentValidationInfo(
        ValidationStatus status,
        UUID customerId,
        AppointmentMarker dto,
        Long intervalId
) { }