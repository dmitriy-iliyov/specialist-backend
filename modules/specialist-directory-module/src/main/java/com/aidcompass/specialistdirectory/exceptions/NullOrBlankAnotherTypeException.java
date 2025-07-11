package com.aidcompass.exceptions;

import com.aidcompass.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.general.exceptions.models.dto.ErrorDto;

public class NullOrBlankAnotherTypeException extends BaseInvalidInputException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("another_type", "Another type is required.");
    }
}
