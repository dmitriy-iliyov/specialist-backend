package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class AccountNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("account", "Account not found.");
    }
}
