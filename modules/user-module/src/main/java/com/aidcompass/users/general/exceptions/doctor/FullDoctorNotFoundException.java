package com.aidcompass.users.general.exceptions.doctor;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class FullDoctorNotFoundException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("full_doctor", "Doctor not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
