package com.aidcompass.schedule.interval.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;


public record IntervalCreateDto(

        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Interval start time shouldn't be null!")
        LocalTime start,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "Interval date shouldn't be null!")
        @Future(message = "Interval should be in future!")
        LocalDate date
) { }
