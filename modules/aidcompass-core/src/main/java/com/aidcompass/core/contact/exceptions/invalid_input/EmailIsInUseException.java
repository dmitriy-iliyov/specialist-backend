package com.aidcompass.core.contact.exceptions.invalid_input;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class EmailIsInUseException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("email", "Email already in use!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
