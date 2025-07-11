package com.aidcompass.users.general.exceptions;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class UserAlreadyExistException extends BaseInvalidInputException {

    private final ErrorDto errorDto = new ErrorDto("user", "User with this id already exists!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
