package com.specialist.auth.exceptions;

import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class RefreshTokenIdNullException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("refresh_token_id", "Refresh token accountId is null.");
    }
}
