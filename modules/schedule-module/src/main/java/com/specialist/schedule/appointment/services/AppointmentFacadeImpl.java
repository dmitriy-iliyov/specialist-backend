package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.specialist.schedule.appointment.validation.AppointmentOwnershipValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentFacadeImpl implements AppointmentFacade {

    private final AppointmentManagementOrchestrator managementOrchestrator;
    private final AppointmentOwnershipValidator ownershipValidator;
    private final AppointmentService service;

    @Override
    public AppointmentResponseDto save(UUID userId, AppointmentCreateDto dto) {
        return managementOrchestrator.save(userId, dto);
    }

    @Override
    public AppointmentResponseDto update(UUID userId, AppointmentUpdateDto updateDto) {
        ownershipValidator.validateForUser(userId, updateDto.getId());
        return managementOrchestrator.update(userId, updateDto);
    }

    @Override
    public AppointmentResponseDto complete(UUID participantId, Long id, String review) {
        ownershipValidator.validateForParticipant(participantId, id);
        return managementOrchestrator.complete(id, review);
    }

    @Override
    public AppointmentResponseDto cancel(UUID participantId, Long id) {
        ownershipValidator.validateForParticipant(participantId, id);
        return managementOrchestrator.cancel(id);
    }

    @Override
    public AppointmentResponseDto findById(UUID participantId, Long id) {
        ownershipValidator.validateForParticipant(participantId, id);
        return service.findById(id);
    }
}
