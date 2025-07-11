package com.aidcompass.core.general.exceptions.models;

import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public abstract class Exception extends RuntimeException {


    public Exception() {

    }

    public Exception(String message) {
        super(message);
    }

    abstract public ErrorDto getErrorDto();
}
