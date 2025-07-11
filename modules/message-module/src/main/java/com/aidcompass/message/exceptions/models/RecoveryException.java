package com.aidcompass.message.exceptions.models;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class RecoveryException extends BaseInvalidInputException {

    private final ErrorDto errorDto;

    public RecoveryException(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
