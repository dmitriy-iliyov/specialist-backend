package com.specialist.auth.exceptions;

import com.specialist.core.exceptions.models.BaseInternalServerException;
import com.specialist.core.exceptions.models.dto.ErrorDto;

public class OAuth2UserAttributesNullException extends BaseInternalServerException {
    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("oauth2_user_attributes", "OAuth2 user attributes is null.");
    }
}
