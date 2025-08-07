package com.specialist.auth.exceptions;


import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnknownUnableReasonTypeException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("unable_reason_type", "Unknown reason type code.");
    }
}
