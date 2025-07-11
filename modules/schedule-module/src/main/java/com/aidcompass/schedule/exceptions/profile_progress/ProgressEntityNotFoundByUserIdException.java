package com.aidcompass.schedule.exceptions.profile_progress;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class ProgressEntityNotFoundByUserIdException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("profile_progress", "Profile progress not found!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
