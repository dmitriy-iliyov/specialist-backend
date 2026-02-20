package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnsupportedGenderException extends BaseBadRequestException {

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("gender", "Unsupported gender");
    }
}
