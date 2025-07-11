package com.aidcompass.schedule.appointment.services;

import com.aidcompass.schedule.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.schedule.appointment.models.dto.StatusFilter;
import com.aidcompass.schedule.appointment.models.enums.AppointmentAgeType;
import com.aidcompass.schedule.appointment.models.enums.AppointmentStatus;
import com.aidcompass.core.general.contracts.dto.PageResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponseDto save(UUID customerId, AppointmentCreateDto dto);

    Map<AppointmentAgeType, AppointmentResponseDto> update(AppointmentUpdateDto dto);

    AppointmentResponseDto findById(Long id);

    PageResponse<AppointmentResponseDto> findAllByStatusFilter(UUID participantId, StatusFilter filter,
                                                               int page, int size, boolean forVolunteer);

    List<AppointmentResponseDto> findAllByVolunteerIdAndDateAndStatus(UUID volunteerId, LocalDate date,
                                                                      AppointmentStatus status);

    List<LocalDate> findMonthDatesByVolunteerId(UUID volunteerId, LocalDate start, LocalDate end);

    AppointmentResponseDto completeById(Long id, String review);

    AppointmentResponseDto cancelById(Long id);

    void cancelAllByDate(UUID participantId, LocalDate date);

    void deleteAll(UUID participantId);

    List<AppointmentResponseDto> findAllByCustomerIdAndDateAndStatus(UUID customerId, LocalDate date, AppointmentStatus appointmentStatus);
}
