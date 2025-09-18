package com.specialist.schedule.appointment.validation;

import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;

import java.util.UUID;

public interface AppointmentOwnershipValidator {
    AppointmentResponseDto validateUserOwnership(UUID userId, Long appointmentId);

    AppointmentResponseDto validateParticipantOwnership(UUID participantId, Long appointmentId);
}
