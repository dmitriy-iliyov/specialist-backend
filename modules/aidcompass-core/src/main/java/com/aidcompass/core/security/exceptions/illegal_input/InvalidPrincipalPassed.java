package com.aidcompass.core.security.exceptions.illegal_input;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class InvalidPrincipalPassed extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("cookie", "Invalid auth token passed!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
