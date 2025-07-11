package com.aidcompass.users.general.exceptions.jurist;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class UnsupportedJuristSpecializationException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("specialization", "Unsupported jurist specialization!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
