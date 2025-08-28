package com.specialist.auth.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class InvalidOldPasswordException extends BaseBadRequestException {

    private final String paramName;

    public InvalidOldPasswordException(String paramName) {
        super();
        this.paramName = paramName;
    }

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto(paramName, "Password not matches with exists");
    }
}
