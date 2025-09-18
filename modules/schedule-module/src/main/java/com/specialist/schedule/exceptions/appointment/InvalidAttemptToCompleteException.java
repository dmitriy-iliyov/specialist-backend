package com.specialist.schedule.exceptions.appointment;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class InvalidAttemptToCompleteException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "Deleted appointment can't be complete!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
