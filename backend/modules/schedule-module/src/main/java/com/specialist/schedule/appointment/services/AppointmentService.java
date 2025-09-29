package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.specialist.schedule.appointment.models.enums.AppointmentAgeType;
import com.specialist.schedule.appointment.models.enums.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AppointmentService extends AppointmentBatchCancelService {
    AppointmentResponseDto save(UUID userId, AppointmentCreateDto dto);

    Map<AppointmentAgeType, AppointmentResponseDto> update(AppointmentUpdateDto dto);

    AppointmentResponseDto findById(Long id);

    List<AppointmentResponseDto> findAllBySpecialistIdAndDateAndStatus(UUID specialistId, LocalDate date,
                                                                       AppointmentStatus status);

    List<LocalDate> findBySpecialistIdAndDateInterval(UUID specialistId, LocalDate start, LocalDate end);

    AppointmentResponseDto completeById(Long id, String review);

    AppointmentResponseDto cancelById(Long id);

    List<AppointmentResponseDto> findAllByUserIdAndDateAndStatus(UUID userId, LocalDate date,
                                                                 AppointmentStatus appointmentStatus);
}
