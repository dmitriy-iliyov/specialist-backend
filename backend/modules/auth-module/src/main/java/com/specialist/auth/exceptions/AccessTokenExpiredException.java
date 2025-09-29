package com.specialist.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AccessTokenExpiredException extends AuthenticationException {
    public AccessTokenExpiredException() {
        super("Access token has expired.");
    }
}
