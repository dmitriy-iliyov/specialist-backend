package com.aidcompass.schedule.exceptions.interval;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class IntervalNotFoundByIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
