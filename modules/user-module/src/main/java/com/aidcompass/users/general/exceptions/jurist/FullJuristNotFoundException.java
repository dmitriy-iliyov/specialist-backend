package com.aidcompass.users.general.exceptions.jurist;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class FullJuristNotFoundException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("full_jurist", "Jurist not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
