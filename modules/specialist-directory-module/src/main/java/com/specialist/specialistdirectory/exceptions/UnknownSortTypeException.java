package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnknownSortTypeException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("sortBy", "Unknown sort type");

    @Override
    public ErrorDto getErrorDto() {
        return errorDto;
    }
}
