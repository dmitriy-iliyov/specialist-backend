package com.specialist.user.exceptions;

import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class NullUserPersistServiceException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("service", "Service not found.");
    }
}
