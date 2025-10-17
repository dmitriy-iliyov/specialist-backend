package com.specialist.schedule.appointment.validation;

import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.infrastructure.AppointmentService;
import com.specialist.schedule.exceptions.appointment.AppointmentOwnershipException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppointmentOwnershipValidatorImpl implements AppointmentOwnershipValidator {

    private final AppointmentService service;

    @Override
    public AppointmentResponseDto validateForUser(UUID userId, Long appointmentId) {
        AppointmentResponseDto dto = service.findById(appointmentId);
        if (!dto.userId().equals(userId)) {
            throw new AppointmentOwnershipException();
        }
        return dto;
    }

    @Override
    public AppointmentResponseDto validateForParticipant(UUID participantId, Long appointmentId) {
        AppointmentResponseDto dto = service.findById(appointmentId);
        if (!dto.userId().equals(participantId) && !dto.specialistId().equals(participantId)) {
            throw new AppointmentOwnershipException();
        }
        return dto;
    }
}
