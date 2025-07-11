package com.aidcompass.core.general.exceptions.models;

import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class UserNotFoundException extends BaseNotFoundException {

    private static String MESSAGE = "User isn't exist!";
    private final ErrorDto errorDto = new ErrorDto("user", MESSAGE);

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
