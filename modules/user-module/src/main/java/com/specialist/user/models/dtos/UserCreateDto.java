package com.specialist.user.models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto extends BaseDto {

    @JsonIgnore
    private String email;

    @JsonCreator
    public UserCreateDto(@JsonProperty("last_name") String lastName,
                         @JsonProperty("first_name") String firstName,
                         @JsonProperty("second_name") String secondName) {
        super(lastName, firstName, secondName);
    }
}
