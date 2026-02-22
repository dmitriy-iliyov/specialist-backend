package com.specialist.auth.core.web.csrf;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsrfTokenControllerUnitTests {

    @Mock
    private CsrfTokenService csrfTokenService;

    @InjectMocks
    private CsrfTokenController csrfTokenController;

    @Test
    @DisplayName("UT: getToken() should return masked token from service")
    void getToken_shouldReturnMaskedToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String maskedToken = "masked_token";

        when(csrfTokenService.getMaskedToken(request)).thenReturn(new DefaultCsrfToken("headerName", "parameterName", maskedToken));

        ResponseEntity<?> response = csrfTokenController.getToken(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        DefaultCsrfToken returnedToken = (DefaultCsrfToken) response.getBody();
        assertEquals(maskedToken, returnedToken.getToken());
    }
}
