package com.specialist.schedule.exceptions.appointment_duration;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class DurationNotFoundBySpecialistIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("duration", "Appointment duration not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
