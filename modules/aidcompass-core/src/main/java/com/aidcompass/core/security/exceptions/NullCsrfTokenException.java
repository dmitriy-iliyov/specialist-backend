package com.aidcompass.core.security.exceptions;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class NullCsrfTokenException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("csrf_token", "Passed csrf token is null!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
