package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseBadRequestException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class CodeExpiredException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("code", "Code has expired.");
    }
}
