package com.specialist.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class RefreshTokenExpiredException extends AuthenticationException {
    public RefreshTokenExpiredException() {
        super("Refresh token has expired.");
    }
}
