package com.specialist.specialistdirectory.domain.specialist.validation;

import com.specialist.contracts.specialistdirectory.dto.ContactType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberValidationStrategyUnitTests {

    private final PhoneNumberValidationStrategy strategy = new PhoneNumberValidationStrategy();

    @Test
    @DisplayName("UT: getType() should return PHONE_NUMBER")
    void getType_shouldReturnPhoneNumber() {
        assertEquals(ContactType.PHONE_NUMBER, strategy.getType());
    }

    @Test
    @DisplayName("UT: validate() with valid phone number should return true")
    void validate_validPhoneNumber_shouldReturnTrue() {
        assertTrue(strategy.validate("+123456789012"));
    }

    @Test
    @DisplayName("UT: validate() with invalid phone number should return false")
    void validate_invalidPhoneNumber_shouldReturnFalse() {
        assertFalse(strategy.validate("123456789012")); // no +
        assertFalse(strategy.validate("+12345678901")); // too short
        assertFalse(strategy.validate("+1234567890123")); // too long
        assertFalse(strategy.validate("+123a56789012")); // not a digit
    }
}
