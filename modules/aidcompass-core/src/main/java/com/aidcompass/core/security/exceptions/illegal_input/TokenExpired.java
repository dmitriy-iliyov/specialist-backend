package com.aidcompass.core.security.exceptions.illegal_input;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class TokenExpired extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("jwt", "Jwt has expired!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
