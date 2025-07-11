package com.aidcompass.core.general.exceptions.models;

import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

import java.util.List;

public abstract class BaseInvalidInputExceptionList extends Exception {

    public abstract List<ErrorDto> getErrorDtoList();
}
