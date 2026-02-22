package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.exceptions.UnsupportedDisableReasonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisableReasonQueryConverterUnitTests {

    private final DisableReasonQueryConverter converter = new DisableReasonQueryConverter();

    @Test
    @DisplayName("UT: convert() with valid string should return enum")
    void convert_validString_shouldReturnEnum() {
        assertEquals(DisableReason.SOFT_DELETE, converter.convert("SOFT_DELETE"));
        assertEquals(DisableReason.EMAIL_CONFIRMATION_REQUIRED, converter.convert("email_confirmation_required"));
    }

    @Test
    @DisplayName("UT: convert() with null should return null")
    void convert_null_shouldReturnNull() {
        assertNull(converter.convert(null));
    }

    @Test
    @DisplayName("UT: convert() with invalid string should throw exception")
    void convert_invalidString_shouldThrowException() {
        assertThrows(UnsupportedDisableReasonException.class, () -> converter.convert("INVALID"));
    }
}
