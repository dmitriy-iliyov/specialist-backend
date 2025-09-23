package com.specialist.contracts.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record NearestIntervalDto(
        Long id,

        @JsonProperty("specialist_id")
        UUID specialistId,

        @JsonFormat(pattern = "HH:mm")
        LocalTime start,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) { }
