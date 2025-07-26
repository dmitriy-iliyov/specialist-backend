package com.aidcompass.specialistdirectory.exceptions;

import com.aidcompass.core.exceptions.models.BaseInvalidInputException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class UnsupportedLanguageException extends BaseInvalidInputException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("language", "Unsupported language.");
    }
}
