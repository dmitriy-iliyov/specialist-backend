package com.specialist.auth.domain.service_account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultSecretGenerationStrategyUnitTests {

    private final DefaultSecretGenerationStrategy strategy = new DefaultSecretGenerationStrategy();

    @Test
    @DisplayName("UT: generate() should return non-null non-empty string")
    void generate_shouldReturnValidString() {
        String secret = strategy.generate();
        assertNotNull(secret);
        assertTrue(secret.length() > 0);
    }
}
