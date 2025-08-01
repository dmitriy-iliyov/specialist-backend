package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseInternalServerException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class UnknownLockReasonTypeException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("lock_reason_type", "Unknown reason type code.");
    }
}
