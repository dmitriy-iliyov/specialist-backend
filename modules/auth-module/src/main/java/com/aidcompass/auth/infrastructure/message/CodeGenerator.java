package com.aidcompass.auth.infrastructure.message;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class CodeGenerator {

    private final SecureRandom random = new SecureRandom();

    public String generate() {
        int code = random.nextInt(100_000, 100_000_000);
        return String.valueOf(code);
    }
}
