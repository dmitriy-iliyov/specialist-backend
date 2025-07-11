package com.aidcompass.schedule.appointment.models.dto;

import com.aidcompass.schedule.appointment.models.enums.AppointmentStatus;
import com.aidcompass.schedule.appointment.models.enums.AppointmentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;


public record AppointmentResponseDto(
        Long id,

        @JsonProperty("customer_id")
        UUID customerId,

        @JsonProperty("volunteer_id")
        UUID volunteerId,

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
