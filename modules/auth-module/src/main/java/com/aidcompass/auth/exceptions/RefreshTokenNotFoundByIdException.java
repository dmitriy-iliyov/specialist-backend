package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class RefreshTokenNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("refresh_token", "Refresh token not found.");
    }
}
