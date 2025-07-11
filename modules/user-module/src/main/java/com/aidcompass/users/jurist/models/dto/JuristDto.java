package com.aidcompass.users.jurist.models.dto;

import com.aidcompass.users.general.dto.BaseVolunteerRegistrationDto;
import com.aidcompass.core.general.utils.validation.ValidEnum;
import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.users.jurist.specialization.models.JuristType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.Set;

@Getter
public class JuristDto extends BaseVolunteerRegistrationDto {

        @NotEmpty(message = "Should contain at least one specialization!")
        @Valid
        private final Set<@ValidEnum(enumClass = JuristSpecialization.class,
                                     message = "Unsupported jurist specialization!") String> specializations;

        @ValidEnum(enumClass = JuristType.class, message = "Unsupported jurist type!")
        private final String type;


        @JsonCreator
        public JuristDto(@JsonProperty("last_name") String lastName,
                         @JsonProperty("first_name") String firstName,
                         @JsonProperty("second_name") String secondName,
                         @JsonProperty("gender") String gender,
                         @JsonProperty("type") String type,
                         @JsonProperty("specializations") Set<String> specializations,
                         @JsonProperty("specialization_detail") String specializationDetail,
                         @JsonProperty("working_experience") Integer workingExperience) {
                super(lastName, firstName, secondName, gender, specializationDetail, workingExperience);
                this.type = type;
                this.specializations = specializations;
        }
}
