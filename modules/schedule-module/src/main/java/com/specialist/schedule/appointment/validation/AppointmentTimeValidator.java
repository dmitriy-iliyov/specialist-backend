package com.specialist.schedule.appointment.validation;

import com.specialist.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentValidationInfo;
import com.specialist.schedule.appointment.models.marker.AppointmentMarker;

import java.util.UUID;

public interface AppointmentTimeValidator {
    AppointmentValidationInfo validateSpecialistTime(UUID userId, AppointmentMarker marker);

    void validateUserTime(UUID userId, AppointmentMarker marker);

    void validateUserTime(UUID userId, Long id, AppointmentUpdateDto update);

    void isCompletePermit(Long id);

    void isCancelPermit(Long id);
}
