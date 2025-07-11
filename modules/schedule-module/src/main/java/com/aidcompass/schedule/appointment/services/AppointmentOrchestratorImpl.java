package com.aidcompass.schedule.appointment.services;

import com.aidcompass.schedule.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentValidationInfo;
import com.aidcompass.schedule.appointment.models.enums.AppointmentAgeType;
import com.aidcompass.schedule.appointment.models.enums.AppointmentStatus;
import com.aidcompass.schedule.appointment.models.enums.ValidationStatus;
import com.aidcompass.schedule.appointment.validation.AppointmentOwnershipValidator;
import com.aidcompass.schedule.appointment.validation.AppointmentTimeValidator;
import com.aidcompass.schedule.appointment_duration.AppointmentDurationService;
import com.aidcompass.schedule.exceptions.appointment.InvalidAttemptToCompleteException;
import com.aidcompass.schedule.exceptions.appointment.InvalidAttemptToDeleteException;
import com.aidcompass.schedule.exceptions.appointment.NotWorkingAtThisTimeException;
import com.aidcompass.schedule.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.schedule.interval.services.IntervalOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AppointmentOrchestratorImpl implements AppointmentOrchestrator {

    private final AppointmentService service;
    private final AppointmentDurationService durationService;
    private final IntervalOrchestrator intervalOrchestrator;
    private final AppointmentTimeValidator timeValidator;
    private final AppointmentOwnershipValidator ownershipValidator;


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public AppointmentResponseDto save(UUID customerId, AppointmentCreateDto dto) {
        Long duration = durationService.findByOwnerId(dto.getVolunteerId());
        dto.setEnd(dto.getStart().plusMinutes(duration));

        timeValidator.validateCustomerTime(customerId, dto);
        AppointmentValidationInfo info = timeValidator.validateVolunteerTime(customerId, dto);

        if (info.status() == ValidationStatus.MATCHES_WITH_INTERVAL) {
            intervalOrchestrator.delete(dto.getVolunteerId(), info.intervalId());
            return service.save(customerId, dto);
        } else if (info.status() == ValidationStatus.APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL) {
            intervalOrchestrator.cut(info.dto(), info.intervalId());
            return service.save(customerId, dto);
        } else {
            throw new NotWorkingAtThisTimeException();
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public AppointmentResponseDto update(UUID customerId, AppointmentUpdateDto updateDto) {

        AppointmentResponseDto currentDto = ownershipValidator.validateCustomerOwnership(customerId, updateDto.getId());

        UUID volunteerId = currentDto.volunteerId();
        updateDto.setVolunteerId(volunteerId);

        Long duration = durationService.findByOwnerId(volunteerId);
        updateDto.setEnd(updateDto.getStart().plusMinutes(duration));

        AppointmentValidationInfo info = null;
        if (!updateDto.getDate().equals(currentDto.date()) || !updateDto.getStart().equals(currentDto.start())) {
            timeValidator.validateCustomerTime(customerId, updateDto.getId(), updateDto);
            info = timeValidator.validateVolunteerTime(customerId, updateDto);
        }

        Map<AppointmentAgeType, AppointmentResponseDto> responseMap;
        if (info == null) {
            return service.update(updateDto).get(AppointmentAgeType.NEW);
        } else if (info.status() == ValidationStatus.MATCHES_WITH_INTERVAL) {
            intervalOrchestrator.delete(updateDto.getVolunteerId(), info.intervalId());
            responseMap = service.update(updateDto);
            AppointmentResponseDto old = responseMap.get(AppointmentAgeType.OLD);
            intervalOrchestrator.systemSave(
                    old.volunteerId(),
                    new SystemIntervalCreatedDto(old.start(), old.end(), old.date())
            );
            return responseMap.get(AppointmentAgeType.NEW);
        } else if (info.status() == ValidationStatus.APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL) {
            intervalOrchestrator.cut(info.dto(), info.intervalId());
            responseMap = service.update(updateDto);
            AppointmentResponseDto old = responseMap.get(AppointmentAgeType.OLD);
            intervalOrchestrator.systemSave(
                    old.volunteerId(),
                    new SystemIntervalCreatedDto(old.start(), old.end(), old.date())
            );
            return responseMap.get(AppointmentAgeType.NEW);
        } else {
            throw new NotWorkingAtThisTimeException();
        }
    }

    @Transactional
    @Override
    public AppointmentResponseDto complete(UUID participantId, Long id, String review) {
        AppointmentResponseDto dto = ownershipValidator.validateParticipantOwnership(participantId, id);
        timeValidator.isCompletePermit(id);
        if (dto.status().equals(AppointmentStatus.CANCELED)) {
            throw new InvalidAttemptToCompleteException();
        }
        return service.completeById(id, review);
    }

    @Transactional
    @Override
    public AppointmentResponseDto cancel(UUID participantId, Long id) {
        AppointmentResponseDto dto = ownershipValidator.validateParticipantOwnership(participantId, id);
        timeValidator.isCancelPermit(id);
        if (dto.status().equals(AppointmentStatus.CANCELED)) {
            throw new InvalidAttemptToDeleteException();
        }
        dto = service.cancelById(id);
        intervalOrchestrator.systemSave(dto.volunteerId(), new SystemIntervalCreatedDto(dto.start(), dto.end(), dto.date()));
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public AppointmentResponseDto findByVolunteerIdAndId(UUID volunteerId, Long id) {
        ownershipValidator.validateParticipantOwnership(volunteerId, id);
        return service.findById(id);
    }
}