package com.specialist.schedule.exceptions.interval;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class IntervalIsInvalidException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("interval", "Start of work interval is after end!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
