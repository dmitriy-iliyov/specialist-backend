package com.specialist.schedule.exceptions.appointment;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class InvalidAttemptToCompleteException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("appointments", "Deleted appointments can't be complete!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
