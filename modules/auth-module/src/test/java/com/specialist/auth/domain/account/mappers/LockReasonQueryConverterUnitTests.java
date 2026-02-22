package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.exceptions.UnsupportedLockReasonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LockReasonQueryConverterUnitTests {

    private final LockReasonQueryConverter converter = new LockReasonQueryConverter();

    @Test
    @DisplayName("UT: convert() with valid string should return enum")
    void convert_validString_shouldReturnEnum() {
        assertEquals(LockReason.ABUSE, converter.convert("ABUSE"));
        assertEquals(LockReason.TYPE_SPAM, converter.convert("type_spam"));
    }

    @Test
    @DisplayName("UT: convert() with null should return null")
    void convert_null_shouldReturnNull() {
        assertNull(converter.convert(null));
    }

    @Test
    @DisplayName("UT: convert() with invalid string should throw exception")
    void convert_invalidString_shouldThrowException() {
        assertThrows(UnsupportedLockReasonException.class, () -> converter.convert("INVALID"));
    }
}
