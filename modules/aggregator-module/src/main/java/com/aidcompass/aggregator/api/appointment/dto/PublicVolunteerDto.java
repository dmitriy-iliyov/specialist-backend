package com.aidcompass.aggregator.api.appointment.dto;

import com.aidcompass.users.gender.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record PublicVolunteerDto(
        UUID id,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("second_name")
        String secondName,

        @JsonProperty("last_name")
        String lastName,

        List<String> specializations,

        @JsonProperty("specialization_detail")
        String specializationDetail,

        @JsonProperty("working_experience")
        Integer workingExperience,

        Gender gender
) { }
