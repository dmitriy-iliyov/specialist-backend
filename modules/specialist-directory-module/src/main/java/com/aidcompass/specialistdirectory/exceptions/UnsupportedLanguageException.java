package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.exceptions.models.BaseBadRequestException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class UnsupportedLanguageException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("language", "Unsupported language.");
    }
}
