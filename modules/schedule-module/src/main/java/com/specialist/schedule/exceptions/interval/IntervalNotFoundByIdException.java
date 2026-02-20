package com.specialist.schedule.exceptions.interval;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;


public class IntervalNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
