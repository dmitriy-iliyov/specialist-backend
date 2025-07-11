package com.aidcompass.aggregator.api.jurist.dto;

import com.aidcompass.schedule.interval.models.dto.NearestIntervalDto;
import com.aidcompass.users.jurist.models.dto.PublicJuristResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record JuristCardDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("jurist")
        PublicJuristResponseDto fullJurist,

        @JsonProperty("nearest_interval")
        NearestIntervalDto nearestInterval,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
