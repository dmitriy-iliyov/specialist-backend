package com.aidcompass.user.exceptions;

import com.aidcompass.core.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class RatingUpdateEventEntityNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("event", "Event not found.");
    }
}
