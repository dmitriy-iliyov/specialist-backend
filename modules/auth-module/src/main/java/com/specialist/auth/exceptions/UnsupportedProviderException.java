package com.specialist.auth.exceptions;

import com.specialist.core.exceptions.models.BaseBadRequestException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class UnsupportedProviderException extends BaseBadRequestException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("provider", "Unsupported provider.");
    }
}
