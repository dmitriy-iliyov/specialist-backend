package com.specialist.auth.core.web.csrf;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CsrfTokenMaskerImplUnitTests {

    private final CsrfTokenMaskerImpl csrfTokenMasker = new CsrfTokenMaskerImpl();

    @Test
    @DisplayName("UT: mask() should return a masked token")
    void mask_shouldReturnMaskedToken() {
        String originalToken = "test_csrf_token";
        String maskedToken = csrfTokenMasker.mask(originalToken);

        assertNotNull(maskedToken);
        assertNotEquals(originalToken, maskedToken);
    }
}
