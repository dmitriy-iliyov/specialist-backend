package com.aidcompass.users.customer.models;

import com.aidcompass.users.general.dto.BaseRegistrationDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CustomerDto extends BaseRegistrationDto {

        @JsonProperty("birthday_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private final LocalDate birthdayDate;

        @JsonCreator
        public CustomerDto(@JsonProperty("last_name") String lastName,
                           @JsonProperty("first_name") String firstName,
                           @JsonProperty("second_name") String secondName,
                           @JsonProperty("birthday_date") LocalDate birthdayDate,
                           @JsonProperty("gender") String gender) {
                super(lastName, firstName, secondName, gender);
                this.birthdayDate = birthdayDate;
        }

}
