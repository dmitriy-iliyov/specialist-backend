package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.exceptions.models.BaseBadRequestException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;


public class NullOrBlankAnotherTypeException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("another_type", "Another type is required.");
    }
}
