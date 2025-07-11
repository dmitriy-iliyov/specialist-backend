package com.aidcompass.users.general.dto;

import com.aidcompass.users.gender.Gender;
import com.aidcompass.core.general.utils.validation.ValidEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class BaseRegistrationDto {

        @JsonProperty("last_name")
        @NotBlank(message = "Last name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(
                regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                message = "Last name should contain only Ukrainian!"
        )
        protected final String lastName;

        @JsonProperty("first_name")
        @NotBlank(message = "First name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(
                regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                message = "First name should contain only Ukrainian!"
        )
        protected final String firstName;

        @JsonProperty("second_name")
        @NotBlank(message = "Second name shouldn't be empty or blank!")
        @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
        @Pattern(
                regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                message = "Second name should contain only Ukrainian!"
        )
        protected final String secondName;

        @ValidEnum(enumClass = Gender.class, message = "Unsupported gender!")
        protected final String gender;
}