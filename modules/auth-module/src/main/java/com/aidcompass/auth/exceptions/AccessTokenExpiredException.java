package com.aidcompass.auth.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AccessTokenExpiredException extends AuthenticationException {
    public AccessTokenExpiredException() {
        super("Access token has expired.", null);
    }
}
