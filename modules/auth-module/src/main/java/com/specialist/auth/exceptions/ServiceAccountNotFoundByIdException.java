package com.specialist.auth.exceptions;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class ServiceAccountNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("service_account", "Not found by accountId.");
    }
}
