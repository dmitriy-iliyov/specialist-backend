package com.aidcompass.core.security.exceptions.illegal_input;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class EmailAllReadyExistException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("email", "Email isn't unique!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
