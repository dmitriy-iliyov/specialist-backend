package com.aidcompass.user.models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCreateDto extends BaseUserDto {

        @JsonCreator
        public UserCreateDto(@JsonProperty("last_name") String lastName,
                             @JsonProperty("first_name") String firstName,
                             @JsonProperty("second_name") String secondName) {
                super(lastName, firstName, secondName);
        }
}
