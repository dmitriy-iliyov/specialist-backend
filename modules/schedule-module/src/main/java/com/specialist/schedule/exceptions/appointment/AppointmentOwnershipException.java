package com.specialist.schedule.exceptions.appointment;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class AppointmentOwnershipException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "This is not your appointment!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
