package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.DisableReason;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UnableReasonTypeConverterUnitTests {

    private final UnableReasonTypeConverter converter = new UnableReasonTypeConverter();

    @Test
    @DisplayName("UT: convertToDatabaseColumn() should return code")
    void convertToDatabaseColumn_shouldReturnCode() {
        assertEquals(1, converter.convertToDatabaseColumn(DisableReason.EMAIL_CONFIRMATION_REQUIRED));
        assertEquals(5, converter.convertToDatabaseColumn(DisableReason.SOFT_DELETE));
    }

    @Test
    @DisplayName("UT: convertToDatabaseColumn() with null should return null")
    void convertToDatabaseColumn_withNull_shouldReturnNull() {
        assertNull(converter.convertToDatabaseColumn(null));
    }

    @Test
    @DisplayName("UT: convertToEntityAttribute() should return enum")
    void convertToEntityAttribute_shouldReturnEnum() {
        assertEquals(DisableReason.EMAIL_CONFIRMATION_REQUIRED, converter.convertToEntityAttribute(1));
        assertEquals(DisableReason.SOFT_DELETE, converter.convertToEntityAttribute(5));
    }

    @Test
    @DisplayName("UT: convertToEntityAttribute() with null should return null")
    void convertToEntityAttribute_withNull_shouldReturnNull() {
        assertNull(converter.convertToEntityAttribute(null));
    }
}
