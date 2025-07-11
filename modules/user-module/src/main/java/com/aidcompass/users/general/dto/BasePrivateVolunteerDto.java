package com.aidcompass.users.general.dto;

import com.aidcompass.users.gender.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;


public record BasePrivateVolunteerDto(
        UUID id,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("second_name")
        String secondName,

        Gender gender,

        @JsonProperty("specialization_detail")
        String specializationDetail,

        @JsonProperty("working_experience")
        Integer workingExperience,

        @JsonProperty("is_approved")
        boolean isApproved,

        @JsonProperty("profile_progress")
        int profileProgress,

        @JsonProperty("profile_status")
        String profileStatus,

        @JsonProperty("created_at")
        Instant createdAt,

        @JsonProperty("updated_at")
        Instant updatedAt
) {}

