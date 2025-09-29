package com.specialist.schedule.appointment.validation;

import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;

import java.util.UUID;

public interface AppointmentOwnershipValidator {
    AppointmentResponseDto validateForUser(UUID userId, Long appointmentId);

    AppointmentResponseDto validateForParticipant(UUID participantId, Long appointmentId);
}
