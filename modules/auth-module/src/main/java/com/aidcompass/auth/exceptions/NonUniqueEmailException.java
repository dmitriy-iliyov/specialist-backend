package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseBadRequestException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class NonUniqueEmailException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("email", "Email isn't unique.");
    }
}
