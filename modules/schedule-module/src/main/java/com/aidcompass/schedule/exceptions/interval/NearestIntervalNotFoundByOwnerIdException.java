package com.aidcompass.schedule.exceptions.interval;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class NearestIntervalNotFoundByOwnerIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("nearest_work_interval", "Nearest interval not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
