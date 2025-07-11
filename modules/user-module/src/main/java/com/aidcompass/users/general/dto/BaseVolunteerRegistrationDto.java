package com.aidcompass.users.general.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public abstract class BaseVolunteerRegistrationDto extends BaseRegistrationDto {

    @JsonProperty("specialization_detail")
    @NotEmpty(message = "Specialization detail shouldn't be empty or blank!")
    @Size(max = 100, message = "Specialization detail shouldn be less then 100 characters!")
    protected final String specializationDetail;

    @JsonProperty("working_experience")
    @Digits(integer = 2, fraction = 0, message = "Working experience can't contain more than 2 digits!")
    protected final Integer workingExperience;

    public BaseVolunteerRegistrationDto(String lastName, String firstName, String secondName, String gender, String specializationDetail, Integer workingExperience) {
        super(lastName, firstName, secondName, gender);
        this.specializationDetail = specializationDetail;
        this.workingExperience = workingExperience;
    }

}
