package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class ReviewSerializeException extends BaseBadRequestException {

    private final ErrorDto errorDto;

    public ReviewSerializeException(String message) {
        errorDto = new ErrorDto("review", "Exception when serializing payload");
    }

    @Override
    public ErrorDto getErrorDto() {
        return errorDto;
    }
}
