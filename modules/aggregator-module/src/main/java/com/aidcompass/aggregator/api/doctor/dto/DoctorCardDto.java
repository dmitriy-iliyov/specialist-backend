package com.aidcompass.aggregator.api.doctor.dto;

import com.aidcompass.users.doctor.models.dto.PublicDoctorResponseDto;
import com.aidcompass.schedule.interval.models.dto.NearestIntervalDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DoctorCardDto(
        @JsonProperty("avatar_url")
        String avatarUrl,

        @JsonProperty("doctor")
        PublicDoctorResponseDto fullDoctor,

        @JsonProperty("nearest_interval")
        NearestIntervalDto nearestInterval,

        @JsonProperty("appointment_duration")
        Long appointmentDuration
) { }
