package com.aidcompass.schedule.interval.models.dto;

import com.aidcompass.schedule.interval.models.marker.IntervalMarker;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record IntervalResponseDto(
        Long id,

        @JsonProperty("owner_id")
        UUID ownerId,

        @JsonFormat(pattern = "HH:mm")
        LocalTime start,

        @JsonFormat(pattern = "HH:mm")
        LocalTime end,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) implements IntervalMarker { }

