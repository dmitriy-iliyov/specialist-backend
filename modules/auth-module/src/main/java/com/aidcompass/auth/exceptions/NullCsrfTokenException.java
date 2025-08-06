package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseBadRequestException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class NullCsrfTokenException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("csrf_token", "Passed csrf token is null!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
