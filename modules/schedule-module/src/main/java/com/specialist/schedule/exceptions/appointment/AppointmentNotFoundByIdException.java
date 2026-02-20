package com.specialist.schedule.exceptions.appointment;


import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class AppointmentNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("appointments", "Appointment not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
