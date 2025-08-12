package com.specialist.auth.exceptions;

import com.specialist.core.exceptions.models.BaseForbiddenException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class OAuth2RegisteredAttemptedToLocalLoginException extends BaseForbiddenException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("", "Account registered with social login, local login is forbidden.");
    }
}
