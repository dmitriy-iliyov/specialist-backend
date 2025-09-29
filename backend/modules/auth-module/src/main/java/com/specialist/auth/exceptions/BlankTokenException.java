package com.specialist.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class BlankTokenException extends AuthenticationException {
    public BlankTokenException() {
        super("Token is required.");
    }
}
