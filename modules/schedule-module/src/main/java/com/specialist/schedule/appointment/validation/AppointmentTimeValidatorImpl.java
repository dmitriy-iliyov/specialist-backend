package com.specialist.schedule.appointment.validation;

import com.specialist.schedule.appointment.infrastructure.AppointmentService;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentValidationInfo;
import com.specialist.schedule.appointment.models.enums.AppointmentStatus;
import com.specialist.schedule.appointment.models.enums.ValidationStatus;
import com.specialist.schedule.appointment.models.marker.AppointmentMarker;
import com.specialist.schedule.appointment_duration.AppointmentDurationService;
import com.specialist.schedule.exceptions.appointment.AppointmentAlreadyExistException;
import com.specialist.schedule.exceptions.appointment.InvalidTimeToCancelException;
import com.specialist.schedule.exceptions.appointment.InvalidTimeToCompleteException;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentTimeValidatorImpl implements AppointmentTimeValidator {

    private final IntervalService intervalService;
    private final AppointmentService appointmentService;
    private final AppointmentDurationService appointmentDurationService;

    @Override
    public AppointmentValidationInfo validateSpecialistTime(UUID userId, AppointmentMarker marker) {
        List<IntervalResponseDto> existingIntervals = intervalService.findAllBySpecialistIdAndDate(marker.getSpecialistId(), marker.getDate());
        if (!existingIntervals.isEmpty()) {
            for (IntervalResponseDto existedInterval: existingIntervals) {
                if (existedInterval.start().equals(marker.getStart()) && existedInterval.end().equals(marker.getEnd())) {
                    return new AppointmentValidationInfo(
                            ValidationStatus.MATCHES_WITH_INTERVAL,
                            userId, marker, existedInterval.id()
                    );
                }
                if (!existedInterval.start().isAfter(marker.getStart()) && !existedInterval.end().isBefore(marker.getEnd())) {
                    return new AppointmentValidationInfo(
                            ValidationStatus.APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL,
                            userId, marker, existedInterval.id()
                    );
                }
            }
        }
        return new AppointmentValidationInfo(ValidationStatus.NO, null, null, null);
    }

    @Override
    public void validateUserTime(UUID userId, AppointmentMarker marker) {
        List<AppointmentResponseDto> existingAppointments = appointmentService.findAllByUserIdAndDateAndStatus(
                userId, marker.getDate(), AppointmentStatus.SCHEDULED
        );
        Long duration = appointmentDurationService.findBySpecialistId(marker.getSpecialistId());
        LocalTime end = marker.getStart().plusMinutes(duration);
        for (AppointmentResponseDto dto: existingAppointments) {
            if (marker.getStart().isBefore(dto.end()) && dto.start().isBefore(end)) {
                throw new AppointmentAlreadyExistException();
            }
        }
    }

    @Override
    public void validateUserTime(UUID userId, Long id, AppointmentUpdateDto update) {
        List<AppointmentResponseDto> appointments = appointmentService.findAllByUserIdAndDateAndStatus(
                userId, update.getDate(), AppointmentStatus.SCHEDULED
        );
        Long duration = appointmentDurationService.findBySpecialistId(update.getSpecialistId());
        LocalTime end = update.getStart().plusMinutes(duration);
        for (AppointmentResponseDto dto: appointments) {
            if (update.getStart().isBefore(dto.end()) && dto.start().isBefore(end) && !Objects.equals(dto.id(), id)) {
                throw new AppointmentAlreadyExistException();
            }
        }
    }

    @Override
    public void isCompletePermit(Long id) {
        AppointmentResponseDto dto = appointmentService.findById(id);
        if (!dto.date().equals(LocalDate.now())) {
            throw new InvalidTimeToCompleteException();
        }
    }

    @Override
    public void isCancelPermit(Long id) {
        AppointmentResponseDto dto = appointmentService.findById(id);
        if (dto.date().isBefore(LocalDate.now())) {
            throw new InvalidTimeToCancelException();
        }
    }
}