package com.aidcompass.users.customer.models;


import com.aidcompass.users.gender.Gender;
import com.aidcompass.users.profile_status.models.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record PrivateCustomerResponseDto(
        UUID id,

        @JsonProperty("last_name")
        String lastName,

        @JsonProperty("first_name")
        String firstName,

        @JsonProperty("second_name")
        String secondName,

        @JsonProperty("birthday_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdayDate,

        Gender gender,

        @JsonProperty("profile_status")
        ProfileStatus profileStatus
) { }
