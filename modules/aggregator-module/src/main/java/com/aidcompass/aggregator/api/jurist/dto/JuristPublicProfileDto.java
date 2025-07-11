package com.aidcompass.aggregator.api.jurist.dto;

import com.aidcompass.core.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.users.jurist.models.dto.FullPublicJuristResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record JuristPublicProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("jurist_profile")
        FullPublicJuristResponseDto fullJurist,

        @JsonProperty("contacts")
        List<PublicContactResponseDto> contacts,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
