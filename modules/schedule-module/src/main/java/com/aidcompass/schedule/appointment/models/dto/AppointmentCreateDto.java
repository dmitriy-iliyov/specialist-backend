package com.aidcompass.schedule.appointment.models.dto;

import com.aidcompass.schedule.appointment.models.enums.AppointmentType;
import com.aidcompass.schedule.appointment.models.marker.AppointmentMarker;
import com.aidcompass.core.general.utils.validation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@RequiredArgsConstructor
@Data
public class AppointmentCreateDto implements AppointmentMarker {

        @JsonProperty("volunteer_id")
        @NotNull(message = "Volunteer id shouldn't be empty or blank!")
        private final UUID volunteerId;

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Appointment date must not be null!")
        @Future(message = "Appointment date must be in the future!")
        private final LocalDate date;

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Appointment start time must not be null!")
        private final LocalTime start;

        @JsonIgnore
        private LocalTime end;

        @ValidEnum(enumClass = AppointmentType.class, message = "Unsupported appointment type!")
        private final String type;

        @NotBlank(message = "Description shouldn't be empty or blank!")
        @Size(max = 80, message = "Description should less than 40!")
        private final String description;
}