package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class NullSpecialistStatusException extends BaseBadRequestException {

    private final ErrorDto dto = new ErrorDto("status", "Specialist status is null");

    @Override
    public ErrorDto getErrorDto() {
        return dto;
    }
}
