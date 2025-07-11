package com.aidcompass.core.security.exceptions;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class ServiceNotFoundByServiceNameException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("service", "Service not found by name!");
    }
}
