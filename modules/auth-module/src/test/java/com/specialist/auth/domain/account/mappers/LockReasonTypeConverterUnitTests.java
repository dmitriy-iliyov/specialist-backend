package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.LockReason;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LockReasonTypeConverterUnitTests {

    private final LockReasonTypeConverter converter = new LockReasonTypeConverter();

    @Test
    @DisplayName("UT: convertToDatabaseColumn() should return code")
    void convertToDatabaseColumn_shouldReturnCode() {
        assertEquals(1, converter.convertToDatabaseColumn(LockReason.TYPE_SPAM));
        assertEquals(4, converter.convertToDatabaseColumn(LockReason.ABUSE));
    }

    @Test
    @DisplayName("UT: convertToDatabaseColumn() with null should return null")
    void convertToDatabaseColumn_withNull_shouldReturnNull() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    @DisplayName("UT: convertToEntityAttribute() should return enum")
    void convertToEntityAttribute_shouldReturnEnum() {
        assertEquals(LockReason.TYPE_SPAM, converter.convertToEntityAttribute(1));
        assertEquals(LockReason.ABUSE, converter.convertToEntityAttribute(4));
    }

    @Test
    @DisplayName("UT: convertToEntityAttribute() with null should return null")
    void convertToEntityAttribute_withNull_shouldReturnNull() {
        assertNull(converter.convertToEntityAttribute(null));
    }
}
