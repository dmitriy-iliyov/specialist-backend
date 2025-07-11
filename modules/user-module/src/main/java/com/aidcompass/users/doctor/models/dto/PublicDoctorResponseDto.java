package com.aidcompass.users.doctor.models.dto;

import com.aidcompass.users.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.users.gender.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record PublicDoctorResponseDto(
        UUID id,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("second_name")
        String secondName,

        List<DoctorSpecialization> specializations,

        @JsonProperty("specialization_detail")
        String specializationDetail,

        @JsonProperty("working_experience")
        Integer workingExperience,

        Gender gender
) { }
