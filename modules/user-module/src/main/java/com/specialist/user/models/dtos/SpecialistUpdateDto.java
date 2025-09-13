package com.specialist.user.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialistUpdateDto extends UserUpdateDto {

    private final String aboutMe;

    public SpecialistUpdateDto(String lastName, String firstName, String secondName, String aboutMe) {
        super(lastName, firstName, secondName);
        this.aboutMe = aboutMe;
    }
}
