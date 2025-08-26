package com.specialist.user.models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCreateDto extends BaseUserDto {

        @JsonCreator
        public UserCreateDto(String email,
                             @JsonProperty("last_name") String lastName,
                             @JsonProperty("first_name") String firstName,
                             @JsonProperty("second_name") String secondName) {
                super(email, lastName, firstName, secondName);
        }
}
