package com.specialist.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthorizationHeaderFormatException extends AuthenticationException {
    public AuthorizationHeaderFormatException() {
        super("Invalid header format, expect Bearer token.");
    }
}
