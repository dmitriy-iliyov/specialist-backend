package com.aidcompass.aggregator.api.doctor.dto;

import com.aidcompass.core.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.users.doctor.models.dto.FullPublicDoctorResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DoctorPublicProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("doctor_profile")
        FullPublicDoctorResponseDto fullDoctor,

        @JsonProperty("contacts")
        List<PublicContactResponseDto> contacts,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }