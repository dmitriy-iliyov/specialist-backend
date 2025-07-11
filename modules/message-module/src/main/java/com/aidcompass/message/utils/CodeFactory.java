package com.aidcompass.message.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CodeFactory {

    private static final SecureRandom random = new SecureRandom();


    public static String generate() {
        int code = random.nextInt(100_000, 1_000_000);
        return String.valueOf(code);
    }
}
