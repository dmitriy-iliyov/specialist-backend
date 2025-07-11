package com.aidcompass.aggregator.system.exception;

import com.aidcompass.core.general.exceptions.models.BaseInternalServerException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class UnsupportedUserTypeException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("user_type", "Unsupported user type!");
    }
}
