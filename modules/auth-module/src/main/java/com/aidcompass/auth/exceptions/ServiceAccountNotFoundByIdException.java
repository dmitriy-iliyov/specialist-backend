package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class ServiceAccountNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("service_account", "Not found by id.");
    }
}
