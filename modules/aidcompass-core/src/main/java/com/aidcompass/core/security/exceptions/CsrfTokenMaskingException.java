package com.aidcompass.core.security.exceptions;

import com.aidcompass.core.general.exceptions.models.BaseInternalServerException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class CsrfTokenMaskingException extends BaseInternalServerException {

    private final ErrorDto errorDto = new ErrorDto("csrf_token", "Unexpected exception when masking csrf token!");


    @Override
    public ErrorDto getErrorDto() {
        return this.errorDto;
    }
}
