package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class AccountNotFoundByEmailException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("email", "Email not found.");
    }
}
