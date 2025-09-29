package com.specialist.schedule.exceptions.interval;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class IntervalAlreadyExistsException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("work_interval", "Interval all ready exist!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
