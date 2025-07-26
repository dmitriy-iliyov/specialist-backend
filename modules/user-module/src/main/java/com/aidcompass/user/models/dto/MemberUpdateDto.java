package com.aidcompass.user.models.dto;

import com.aidcompass.user.validation.UniqueEmail;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@UniqueEmail(message = "Email should  be unique.")
public class MemberUpdateDto extends BaseMemberDto {

        @NotBlank(message = "Email is required.")
        @Email(message = "Email should be valid.")
        private final String email;

        @JsonCreator
        public MemberUpdateDto(@JsonProperty("last_name") String lastName,
                               @JsonProperty("first_name") String firstName,
                               @JsonProperty("second_name") String secondName,
                               String email) {
                super(lastName, firstName, secondName);
                this.email = email;
        }
}
