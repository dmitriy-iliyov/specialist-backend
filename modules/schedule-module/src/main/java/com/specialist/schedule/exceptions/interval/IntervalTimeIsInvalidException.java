package com.specialist.schedule.exceptions.interval;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class IntervalTimeIsInvalidException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("interval", "Non-working hours!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
