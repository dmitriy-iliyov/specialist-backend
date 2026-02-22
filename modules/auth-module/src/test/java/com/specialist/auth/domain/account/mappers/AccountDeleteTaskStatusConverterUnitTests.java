package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.enums.AccountDeleteTaskStatus;
import com.specialist.auth.exceptions.UnexpectedAccountDeleteTaskStatusCodeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountDeleteTaskStatusConverterUnitTests {

    private final AccountDeleteTaskStatusConverter converter = new AccountDeleteTaskStatusConverter();

    @Test
    @DisplayName("UT: convertToDatabaseColumn() should return code")
    void convertToDatabaseColumn_shouldReturnCode() {
        assertEquals(1, converter.convertToDatabaseColumn(AccountDeleteTaskStatus.READY_TO_SEND));
        assertEquals(4, converter.convertToDatabaseColumn(AccountDeleteTaskStatus.FAILED));
    }

    @Test
    @DisplayName("UT: convertToEntityAttribute() should return enum")
    void convertToEntityAttribute_shouldReturnEnum() {
        assertEquals(AccountDeleteTaskStatus.READY_TO_SEND, converter.convertToEntityAttribute(1));
        assertEquals(AccountDeleteTaskStatus.FAILED, converter.convertToEntityAttribute(4));
    }

    @Test
    @DisplayName("UT: convertToEntityAttribute() with invalid code should throw exception")
    void convertToEntityAttribute_invalidCode_shouldThrowException() {
        assertThrows(UnexpectedAccountDeleteTaskStatusCodeException.class, () -> converter.convertToEntityAttribute(999));
    }
}
