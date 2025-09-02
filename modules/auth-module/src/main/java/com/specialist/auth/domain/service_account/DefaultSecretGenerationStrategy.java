package com.specialist.auth.domain.service_account;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class DefaultSecretGenerationStrategy implements SecretGenerationStrategy {

    @Override
    public String generate() {
        byte [] secretBytes = new byte[48];
        new SecureRandom().nextBytes(secretBytes);
        return Base64.getUrlEncoder().encodeToString(secretBytes);
    }
}
