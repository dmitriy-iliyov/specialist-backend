package com.specialist.schedule.exceptions.appointment;


import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class AppointmentAlreadyExistException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("appointment", "You already have appointment at this time!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
