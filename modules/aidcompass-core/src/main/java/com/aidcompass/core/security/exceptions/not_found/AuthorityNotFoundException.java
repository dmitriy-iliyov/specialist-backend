package com.aidcompass.core.security.exceptions.not_found;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class AuthorityNotFoundException extends BaseNotFoundException {

    private static String MESSAGE = "Authority isn't exist!";
    private final ErrorDto errorDto = new ErrorDto("authority", MESSAGE);

    public AuthorityNotFoundException() {
        super(MESSAGE);
    }

    public AuthorityNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }

}
