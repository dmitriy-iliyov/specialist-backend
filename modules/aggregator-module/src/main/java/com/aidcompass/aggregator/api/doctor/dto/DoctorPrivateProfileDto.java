package com.aidcompass.aggregator.api.doctor.dto;


import com.aidcompass.core.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.users.doctor.models.dto.FullPrivateDoctorResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DoctorPrivateProfileDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("doctor_profile")
        FullPrivateDoctorResponseDto fullDoctor,

        @JsonProperty("contacts")
        List<PrivateContactResponseDto> contacts,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
