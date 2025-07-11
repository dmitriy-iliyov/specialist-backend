package com.aidcompass.schedule.interval.models.dto;

import com.aidcompass.schedule.interval.models.marker.IntervalMarker;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record SystemIntervalUpdateDto(
        @NotNull(message = "Work interval id shouldn't be null!")
        @Positive(message = "Work interval id should be positive!")
        Long id,

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
