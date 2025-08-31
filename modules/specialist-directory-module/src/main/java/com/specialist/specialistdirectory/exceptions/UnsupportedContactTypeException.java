package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnsupportedContactTypeException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("contact_type", "Unsupported contact type.");
    }
}
