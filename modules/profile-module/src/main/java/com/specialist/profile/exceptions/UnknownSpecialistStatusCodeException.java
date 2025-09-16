package com.specialist.profile.exceptions;

import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnknownSpecialistStatusCodeException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("status_code", "Unknown specialist status code.");
    }
}
