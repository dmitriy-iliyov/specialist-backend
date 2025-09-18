package com.specialist.schedule.interval.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.specialist.schedule.interval.models.marker.IntervalMarker;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record SystemIntervalCreatedDto(

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Work interval start time shouldn't be null!")
        LocalTime start,

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Work interval end time shouldn't be null!")
        LocalTime end,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Work interval date shouldn't be null!")
        LocalDate date
) implements IntervalMarker { }
