package com.specialist.auth.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class InvalidPasswordException extends BaseBadRequestException {

    private final String paramName;

    public InvalidPasswordException(String paramName) {
        super();
        this.paramName = paramName;
    }

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto(paramName, "Invalid password");
    }
}
