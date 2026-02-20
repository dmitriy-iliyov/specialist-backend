package com.specialist.schedule.exceptions.interval;


import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class NearestIntervalNotFoundBySpecialistIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("nearest_work_interval", "Nearest interval not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
