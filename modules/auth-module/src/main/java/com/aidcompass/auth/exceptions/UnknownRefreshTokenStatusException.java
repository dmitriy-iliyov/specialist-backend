package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseInternalServerException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class UnknownRefreshTokenStatusException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("refresh_token_status", "Unknown refresh token status.");
    }
}
