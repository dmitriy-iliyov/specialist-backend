package com.specialist.auth.exceptions;


import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnknownDisableReasonTypeException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("disable_reason_type", "Unknown reason type code.");
    }
}
