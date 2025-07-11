package com.aidcompass.users.general.exceptions;

import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class ProfileStatusEntityNotFoundByStatusException extends BaseNotFoundException {

    private final ErrorDto errorDto = new ErrorDto("profile_status", "ProfileStatus not found!");

    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
