package com.specialist.core.exceptions.models;


import com.specialist.core.exceptions.models.dto.ErrorDto;

import java.util.List;

public abstract class BaseInvalidInputExceptionList extends java.lang.Exception {

    public abstract List<ErrorDto> getErrorDtoList();
}
