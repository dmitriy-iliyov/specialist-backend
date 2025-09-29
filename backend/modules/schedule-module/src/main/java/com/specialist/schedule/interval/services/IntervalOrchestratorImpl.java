package com.specialist.schedule.interval.services;

import com.specialist.schedule.appointment.models.marker.AppointmentMarker;
import com.specialist.schedule.appointment_duration.AppointmentDurationService;
import com.specialist.schedule.exceptions.interval.IntervalIsInvalidException;
import com.specialist.schedule.exceptions.interval.IntervalTimeIsInvalidException;
import com.specialist.schedule.interval.models.dto.IntervalCreateDto;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.models.dto.SystemIntervalCreatedDto;
import com.specialist.schedule.interval.validation.ownership.IntervalOwnershipValidator;
import com.specialist.schedule.interval.validation.time.TimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IntervalOrchestratorImpl implements IntervalOrchestrator {

    private final IntervalService service;
    private final NearestIntervalService nearestService;
    private final AppointmentDurationService appointmentDurationService;
    private final IntervalOwnershipValidator ownershipValidator;
    private final TimeValidator timeValidator;

    @Transactional
    @Override
    public IntervalResponseDto save(UUID specialistId, IntervalCreateDto inputDto) {
        Long duration = appointmentDurationService.findBySpecialistId(specialistId);
        LocalTime end = inputDto.start().plusMinutes(duration);
        SystemIntervalCreatedDto dto = new SystemIntervalCreatedDto(inputDto.start(), end, inputDto.date());

        if (!timeValidator.isIntervalValid(dto)) {
            throw new IntervalIsInvalidException();
        }
        if (!timeValidator.isIntervalTimeValid(dto)) {
            throw new IntervalTimeIsInvalidException();
        }

        IntervalResponseDto existedDto = service.findBySpecialistIdAndStartAndDate(specialistId, dto.date(), dto.start());
        if (existedDto != null) {
            return existedDto;
        }
        existedDto = service.save(specialistId, dto);
        nearestService.replaceIfEarlier(specialistId, existedDto);
        return existedDto;
    }

    @Transactional
    @Override
    public void systemSave(UUID ownerId, SystemIntervalCreatedDto dto) {
        IntervalResponseDto existedDto = service.findBySpecialistIdAndStartAndDate(ownerId, dto.date(), dto.start());
        if (existedDto != null) {
            return;
        }
        IntervalResponseDto responseDto = service.save(ownerId, dto);
        nearestService.replaceIfEarlier(ownerId, responseDto);
    }

    @Override
    public void cut(AppointmentMarker dto, Long id) {
        Set<IntervalResponseDto> resultDtoSet = service.cut(dto, id);
        List<IntervalResponseDto> resultDtoList = resultDtoSet.stream()
                .sorted(Comparator.comparing(IntervalResponseDto::date)
                        .thenComparing(IntervalResponseDto::start))
                .toList();
        nearestService.replaceIfEarlier(dto.getSpecialistId(), resultDtoList.get(0));
    }

    @Override
    public void delete(UUID specialistId, Long id) {
        ownershipValidator.validate(specialistId, id);
        service.deleteBySpecialistIdAndId(specialistId, id);
        nearestService.deleteBySpecialistId(specialistId, id);
    }
}