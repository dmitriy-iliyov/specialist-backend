package com.specialist.profile.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialistCreateDto extends UserCreateDto {

    public SpecialistCreateDto(String lastName, String firstName, String secondName) {
        super(lastName, firstName, secondName);
    }
}
