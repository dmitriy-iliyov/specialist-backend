package com.specialist.schedule.interval.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.schedule.interval.models.marker.IntervalMarker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record IntervalResponseDto(
        Long id,

        @JsonProperty("specialist_id")
        UUID specialistId,

        @JsonFormat(pattern = "HH:mm")
        LocalTime start,

        @JsonFormat(pattern = "HH:mm")
        LocalTime end,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) implements IntervalMarker { }

