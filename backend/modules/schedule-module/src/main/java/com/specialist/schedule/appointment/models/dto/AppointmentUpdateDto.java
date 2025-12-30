package com.specialist.schedule.appointment.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.specialist.schedule.appointment.models.enums.AppointmentType;
import com.specialist.schedule.appointment.models.marker.AppointmentMarker;
import com.specialist.utils.validation.ValidEnum;
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
public class AppointmentUpdateDto implements AppointmentMarker {

        @JsonIgnore
        private Long id;

        @JsonIgnore
        private UUID specialistId;

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Appointment date must not be null!")
        @Future(message = "Appointment date must be in the future!")
        private final LocalDate date;

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Appointment start time must not be null!")
        private final LocalTime start;

        @JsonIgnore
        private LocalTime end;

        @ValidEnum(enumClass = AppointmentType.class, message = "Unsupported appointments type!")
        private final String type;

        @NotBlank(message = "Description shouldn't be empty or blank!")
        @Size(max = 80, message = "Description should less than 40!")
        private final String description;
}