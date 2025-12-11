package com.specialist.schedule.appointment.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.schedule.appointment.infrastructure.AppointmentService;
import com.specialist.schedule.appointment.mapper.AppointmentMapper;
import com.specialist.schedule.appointment.models.dto.*;
import com.specialist.schedule.appointment.validation.AppointmentOwnershipValidator;
import com.specialist.utils.pagination.PageResponse;
import io.github.dmitriyiliyov.springoutbox.publisher.aop.OutboxPublish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentFacadeImpl implements AppointmentFacade {

    private final AppointmentManagementOrchestrator managementOrchestrator;
    private final AppointmentOwnershipValidator ownershipValidator;
    private final AppointmentService service;
    private final AppointmentAggregator aggregator;
    private final AppointmentMapper mapper;
    // private final ApplicationEventPublisher eventPublisher;

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

    @Transactional
    @OutboxPublish(eventType = "canceled-appointment-notification")
    @Override
    public AppointmentResponseDto cancel(UUID participantId, Long id) {
        ownershipValidator.validateForParticipant(participantId, id);
        // eventPublisher.publishEvent(new InternalAppointmentCancelEvent(participantId, List.of(mapper.toSystemDto(dto))));
        return managementOrchestrator.cancel(id);
    }

    @Override
    public AppointmentResponseDto findById(UUID participantId, Long id) {
        ownershipValidator.validateForParticipant(participantId, id);
        return service.findById(id);
    }

    @Override
    public PageResponse<AppointmentAggregatedResponseDto> findAllByFilter(UUID participantId, ProfileType profileType,
                                                                          AppointmentFilter filter) {
        return aggregator.aggregate(participantId, profileType, filter);
    }
}
