package com.specialist.schedule.exceptions.appointment;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class NotWorkingAtThisTimeException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("appointments", "Can't add appointments because specialist not working at this time!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
