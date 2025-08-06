package com.aidcompass.auth.exceptions;

import com.aidcompass.core.exceptions.models.BaseBadRequestException;
import com.aidcompass.core.exceptions.models.dto.ErrorDto;

public class CsrfTokenMaskingException extends BaseBadRequestException {

    private final ErrorDto errorDto = new ErrorDto("csrf_token", "Unexpected exception when masking csrf token!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
