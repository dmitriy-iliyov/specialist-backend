package com.aidcompass.message.exceptions.models;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class InvalidPasswordRecoveryTokenException extends BaseInvalidInputException {

    private static final String MESSAGE = "Recovery code is invalid!";
    private final ErrorDto errorDto = new ErrorDto("code", MESSAGE);

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
