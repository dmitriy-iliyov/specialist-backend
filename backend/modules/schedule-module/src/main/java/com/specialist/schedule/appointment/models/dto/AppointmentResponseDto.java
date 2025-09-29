package com.specialist.schedule.appointment.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.schedule.appointment.models.enums.AppointmentStatus;
import com.specialist.schedule.appointment.models.enums.AppointmentType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


public record AppointmentResponseDto(
        Long id,

        @JsonProperty("user_id")
        UUID userId,

        @JsonProperty("specialist_id")
        UUID specialistId,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @JsonFormat(pattern = "HH:mm")
        LocalTime start,

        @JsonFormat(pattern = "HH:mm")
        LocalTime end,

        AppointmentType type,

        AppointmentStatus status,

        String description
) { }
