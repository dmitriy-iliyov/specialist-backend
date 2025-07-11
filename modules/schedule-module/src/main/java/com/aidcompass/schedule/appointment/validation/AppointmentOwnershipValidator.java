package com.aidcompass.schedule.appointment.validation;

import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.schedule.appointment.services.AppointmentService;
import com.aidcompass.schedule.exceptions.appointment.AppointmentOwnershipException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppointmentOwnershipValidator {

    private final AppointmentService service;


    public AppointmentResponseDto validateCustomerOwnership(UUID customerId, Long appointmentId) {
        AppointmentResponseDto dto = service.findById(appointmentId);
        if (!dto.customerId().equals(customerId)) {
            throw new AppointmentOwnershipException();
        }
        return dto;
    }

    public AppointmentResponseDto validateParticipantOwnership(UUID participantId, Long appointmentId) {
        AppointmentResponseDto dto = service.findById(appointmentId);
        if (!dto.customerId().equals(participantId) && !dto.volunteerId().equals(participantId)) {
            throw new AppointmentOwnershipException();
        }
        return dto;
    }
}
