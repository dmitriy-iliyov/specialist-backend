package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidationStrategyUnitTests {

    private final EmailValidationStrategy strategy = new EmailValidationStrategy();

    @Test
    @DisplayName("UT: getType() should return EMAIL")
    void getType_shouldReturnEmail() {
        assertEquals(ContactType.EMAIL, strategy.getType());
    }

    @Test
    @DisplayName("UT: validate() with valid email should return true")
    void validate_validEmail_shouldReturnTrue() {
        assertTrue(strategy.validate("test@example.com"));
        assertTrue(strategy.validate("user.name@domain.co.uk"));
    }

    @Test
    @DisplayName("UT: validate() with invalid email should return false")
    void validate_invalidEmail_shouldReturnFalse() {
        assertFalse(strategy.validate("invalid"));
        assertFalse(strategy.validate("test@"));
        assertFalse(strategy.validate("@example.com"));
    }
}
