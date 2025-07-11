package com.aidcompass.users.general.exceptions.doctor;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class UnsupportedDoctorSpecializationTypeException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("specialization", "Unsupported doctor specialization!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
