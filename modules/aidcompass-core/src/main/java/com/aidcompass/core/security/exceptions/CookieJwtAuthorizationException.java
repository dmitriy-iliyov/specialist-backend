package com.aidcompass.core.security.exceptions;

import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

public class CookieJwtAuthorizationException extends BaseAuthorizationException {

    private String message;

    public CookieJwtAuthorizationException() {
        this.message = "Request hasn't cookie with name __Host-auth_token!";
    }

    public CookieJwtAuthorizationException(String message) {
        this.message = message;
    }

    @Override
    public ErrorDto getErrorDto() {
        return new ErrorDto("cookie", message);
    }
}
