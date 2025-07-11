package com.aidcompass.users.general.exceptions.doctor;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class DoctorNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("doctor", "Doctor not found by id!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
