package com.aidcompass.users.detail.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DetailDto(

        @JsonProperty("about_myself")
        @Size(max = 235, message = "About myself can't contains more then 235 characters!")
        String aboutMyself,

        @NotBlank(message = "Address can't be empty or blank!")
        String address
) { }
