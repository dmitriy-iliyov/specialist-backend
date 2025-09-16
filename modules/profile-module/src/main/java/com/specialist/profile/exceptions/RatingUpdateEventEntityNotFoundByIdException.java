package com.specialist.profile.exceptions;

import com.specialist.core.exceptions.models.BaseNotFoundException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class RatingUpdateEventEntityNotFoundByIdException extends BaseNotFoundException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("event", "Event not found.");
    }
}
