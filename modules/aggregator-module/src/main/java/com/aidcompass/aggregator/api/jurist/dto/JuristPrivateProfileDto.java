package com.aidcompass.aggregator.api.jurist.dto;

import com.aidcompass.core.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.users.jurist.models.dto.FullPrivateJuristResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record JuristPrivateProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("jurist_profile")
        FullPrivateJuristResponseDto fullJurist,

        @JsonProperty("contacts")
        List<PrivateContactResponseDto> contacts,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
