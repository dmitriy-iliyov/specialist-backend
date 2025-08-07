package com.specialist.core.exceptions.models;

import com.specialist.core.exceptions.models.dto.ErrorDto;

public abstract class Exception extends RuntimeException {


    public Exception() {

    }

    public Exception(String message) {
        super(message);
    }

    abstract public ErrorDto getErrorDto();
}
