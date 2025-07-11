package com.aidcompass.users.general.exceptions.jurist;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class JuristTypeNotFoundByTypeException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("type", "Jurist type not  found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
