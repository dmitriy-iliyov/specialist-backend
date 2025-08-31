package com.specialist.specialistdirectory.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class NoSuchSpecialistContactException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("contact_type", "Specialist have not contact of this type.");
    }
}
