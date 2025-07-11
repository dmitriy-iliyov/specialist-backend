package com.aidcompass.schedule.appointment.validation;

import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentValidationInfo;
import com.aidcompass.schedule.appointment.models.enums.AppointmentStatus;
import com.aidcompass.schedule.appointment.models.enums.ValidationStatus;
import com.aidcompass.schedule.appointment.models.marker.AppointmentMarker;
import com.aidcompass.schedule.appointment.services.AppointmentService;
import com.aidcompass.schedule.appointment_duration.AppointmentDurationService;
import com.aidcompass.schedule.exceptions.appointment.AppointmentAlreadyExistException;
import com.aidcompass.schedule.exceptions.appointment.InvalidTimeToCancelException;
import com.aidcompass.schedule.exceptions.appointment.InvalidTimeToCompleteException;
import com.aidcompass.schedule.interval.models.dto.IntervalResponseDto;
import com.aidcompass.schedule.interval.services.IntervalService;
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
public class AppointmentTimeValidator {

    private final IntervalService intervalService;
    private final AppointmentService appointmentService;
    private final AppointmentDurationService appointmentDurationService;


    public AppointmentValidationInfo validateVolunteerTime(UUID customerId, AppointmentMarker marker) {
        List<IntervalResponseDto> existingDtoList = intervalService.findAllByOwnerIdAndDate(marker.getVolunteerId(), marker.getDate());
        if (!existingDtoList.isEmpty()) {
            for (IntervalResponseDto existedInterval: existingDtoList) {
                if (existedInterval.start().equals(marker.getStart()) && existedInterval.end().equals(marker.getEnd())) {
                    return new AppointmentValidationInfo(
                            ValidationStatus.MATCHES_WITH_INTERVAL,
                            customerId, marker, existedInterval.id()
                    );
                }
                if (!existedInterval.start().isAfter(marker.getStart()) && !existedInterval.end().isBefore(marker.getEnd())) {
                    return new AppointmentValidationInfo(
                            ValidationStatus.APPOINTMENT_INTERVAL_IS_INSIDE_WORK_INTERVAL,
                            customerId, marker, existedInterval.id()
                    );
                }
            }
        }
        return new AppointmentValidationInfo(ValidationStatus.NO, null, null, null);
    }

    public void validateCustomerTime(UUID customerId, AppointmentMarker marker) {
        List<AppointmentResponseDto> appointments = appointmentService.findAllByCustomerIdAndDateAndStatus(
                customerId, marker.getDate(), AppointmentStatus.SCHEDULED
        );
        Long duration = appointmentDurationService.findByOwnerId(marker.getVolunteerId());
        LocalTime end = marker.getStart().plusMinutes(duration);
        for (AppointmentResponseDto dto: appointments) {
            if (marker.getStart().isBefore(dto.end()) && dto.start().isBefore(end)) {
                throw new AppointmentAlreadyExistException();
            }
        }
    }

    public void validateCustomerTime(UUID customerId, Long id, AppointmentUpdateDto update) {
        List<AppointmentResponseDto> appointments = appointmentService.findAllByCustomerIdAndDateAndStatus(
                customerId, update.getDate(), AppointmentStatus.SCHEDULED
        );
        Long duration = appointmentDurationService.findByOwnerId(update.getVolunteerId());
        LocalTime end = update.getStart().plusMinutes(duration);
        for (AppointmentResponseDto dto: appointments) {
            if (update.getStart().isBefore(dto.end()) && dto.start().isBefore(end) && !Objects.equals(dto.id(), id)) {
                throw new AppointmentAlreadyExistException();
            }
        }
    }

    public void isCompletePermit(Long id) {
        AppointmentResponseDto dto = appointmentService.findById(id);
        if (!dto.date().equals(LocalDate.now())) {
            throw new InvalidTimeToCompleteException();
        }
    }

    public void isCancelPermit(Long id) {
        AppointmentResponseDto dto = appointmentService.findById(id);
        if (dto.date().isBefore(LocalDate.now())) {
            throw new InvalidTimeToCancelException();
        }
    }
}