package com.aidcompass.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtParseException extends AuthenticationException {
    public JwtParseException(Exception e) {
        super("Jwt parse exception.", e);
    }
}
