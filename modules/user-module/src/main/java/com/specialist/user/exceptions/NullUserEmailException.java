package com.specialist.user.exceptions;

import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class NullUserEmailException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("email", "User email is null.");
    }
}
