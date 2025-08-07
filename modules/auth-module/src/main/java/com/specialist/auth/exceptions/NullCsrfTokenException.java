package com.specialist.auth.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class NullCsrfTokenException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("csrf_token", "Passed csrf token is null!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
