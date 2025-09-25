package com.specialist.schedule.exceptions.appointment;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class InvalidTimeToCancelException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("appointments", "Past appointments can't be cancel!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
