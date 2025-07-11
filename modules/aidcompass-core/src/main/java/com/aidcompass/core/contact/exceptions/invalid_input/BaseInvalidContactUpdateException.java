package com.aidcompass.core.contact.exceptions.invalid_input;

import com.aidcompass.core.general.exceptions.models.BaseInvalidInputExceptionList;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

import java.util.List;

public class BaseInvalidContactUpdateException extends BaseInvalidInputExceptionList {

    public List<ErrorDto> errorDtoList;


    public BaseInvalidContactUpdateException(List<ErrorDto> errors) {
        this.errorDtoList = errors;
    }


    @Override
    public List<ErrorDto> getErrorDtoList() {
        return errorDtoList;
    }

    @Override
    public ErrorDto getErrorDto() {
        return null;
    }

}
