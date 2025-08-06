package com.aidcompass.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtSignatureException extends AuthenticationException {
    public InvalidJwtSignatureException(Throwable cause) {
        super("Invalid jwt signature.", cause);
    }
}
