package com.specialist.user.models.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.user.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto extends BaseUserDto {

        @JsonCreator
        public UserUpdateDto(String email,
                             @JsonProperty("last_name") String lastName,
                             @JsonProperty("first_name") String firstName,
                             @JsonProperty("second_name") String secondName) {
                super(email, lastName, firstName, secondName);
        }
}
