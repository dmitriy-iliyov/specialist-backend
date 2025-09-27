package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentValidationInfo;
import com.specialist.schedule.appointment.models.enums.AppointmentAgeType;
import com.specialist.schedule.appointment.models.enums.AppointmentStatus;
import com.specialist.schedule.appointment.models.enums.ValidationStatus;
import com.specialist.schedule.appointment.validation.AppointmentTimeValidator;
import com.specialist.schedule.appointment_duration.AppointmentDurationService;
import com.specialist.schedule.exceptions.appointment.InvalidAttemptToCancelException;
import com.specialist.schedule.exceptions.appointment.InvalidAttemptToCompleteException;
import com.specialist.schedule.exceptions.appointment.NotWorkingAtThisTimeException;
import com.specialist.schedule.interval.models.dto.SystemIntervalCreatedDto;
import com.specialist.schedule.interval.services.IntervalOrchestrator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Component
public class AppointmentManagementOrchestratorImpl implements AppointmentManagementOrchestrator {

    private final AppointmentService service;
    private final AppointmentDurationService durationService;
    private final IntervalOrchestrator intervalOrchestrator;
    private final AppointmentTimeValidator timeValidator;

    public AppointmentManagementOrchestratorImpl(AppointmentService service,
                                                 AppointmentDurationService durationService,
                                                 IntervalOrchestrator intervalOrchestrator,
                                                 AppointmentTimeValidator timeValidator) {
        this.service = service;
        this.durationService = durationService;
        this.intervalOrchestrator = intervalOrchestrator;
        this.timeValidator = timeValidator;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public AppointmentResponseDto save(UUID userId, AppointmentCreateDto dto) {
        Long duration = durationService.findBySpecialistId(dto.getSpecialistId());
        dto.setEnd(dto.getStart().plusMinutes(duration));
        timeValidator.validateUserTime(userId, dto);
        AppointmentValidationInfo info = timeValidator.validateSpecialistTime(userId, dto);
        if (info.status() == ValidationStatus.MATCHES_WITH_INTERVAL) {
            intervalOrchestrator.delete(dto.getSpecialistId(), info.intervalId());
            return service.save(userId, dto);
        } else if (info.status() == ValidationStatus.APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL) {
            intervalOrchestrator.cut(info.dto(), info.intervalId());
            return service.save(userId, dto);
        } else {
            throw new NotWorkingAtThisTimeException();
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public AppointmentResponseDto update(UUID userId, AppointmentUpdateDto updateDto) {
        AppointmentResponseDto currentDto = service.findById(updateDto.getId());
        UUID specialistId = currentDto.specialistId();
        updateDto.setSpecialistId(specialistId);
        Long duration = durationService.findBySpecialistId(specialistId);
        updateDto.setEnd(updateDto.getStart().plusMinutes(duration));
        AppointmentValidationInfo info = null;
        if (!updateDto.getDate().equals(currentDto.date()) || !updateDto.getStart().equals(currentDto.start())) {
            timeValidator.validateUserTime(userId, updateDto.getId(), updateDto);
            info = timeValidator.validateSpecialistTime(userId, updateDto);
        }
        Map<AppointmentAgeType, AppointmentResponseDto> responseMap;
        if (info == null) {
            return service.update(updateDto).get(AppointmentAgeType.NEW);
        } else if (info.status() == ValidationStatus.MATCHES_WITH_INTERVAL) {
            intervalOrchestrator.delete(updateDto.getSpecialistId(), info.intervalId());
            responseMap = service.update(updateDto);
            AppointmentResponseDto old = responseMap.get(AppointmentAgeType.OLD);
            intervalOrchestrator.systemSave(
                    old.specialistId(),
                    new SystemIntervalCreatedDto(old.start(), old.end(), old.date())
            );
            return responseMap.get(AppointmentAgeType.NEW);
        } else if (info.status() == ValidationStatus.APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL) {
            intervalOrchestrator.cut(info.dto(), info.intervalId());
            responseMap = service.update(updateDto);
            AppointmentResponseDto old = responseMap.get(AppointmentAgeType.OLD);
            intervalOrchestrator.systemSave(
                    old.specialistId(),
                    new SystemIntervalCreatedDto(old.start(), old.end(), old.date())
            );
            return responseMap.get(AppointmentAgeType.NEW);
        } else {
            throw new NotWorkingAtThisTimeException();
        }
    }

    @Transactional
    @Override
    public AppointmentResponseDto complete(Long id, String review) {
        AppointmentResponseDto dto = service.findById(id);
        timeValidator.isCompletePermit(id);
        if (!dto.status().equals(AppointmentStatus.SCHEDULED)) {
            throw new InvalidAttemptToCompleteException();
        }
        return service.completeById(id, review);
    }

    @Transactional
    @Override
    public AppointmentResponseDto cancel(Long id) {
        AppointmentResponseDto dto = service.findById(id);
        timeValidator.isCancelPermit(id);
        if (!dto.status().equals(AppointmentStatus.SCHEDULED)) {
            throw new InvalidAttemptToCancelException();
        }
        dto = service.cancelById(id);
        intervalOrchestrator.systemSave(dto.specialistId(), new SystemIntervalCreatedDto(dto.start(), dto.end(), dto.date()));
        return dto;
    }
}