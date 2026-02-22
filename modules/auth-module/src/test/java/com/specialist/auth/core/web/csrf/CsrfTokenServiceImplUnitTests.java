package com.specialist.auth.core.web.csrf;

import com.specialist.auth.exceptions.CsrfTokenNullException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsrfTokenServiceImplUnitTests {

    @Mock
    private CsrfTokenRepository csrfTokenRepository;

    @Mock
    private CsrfTokenMasker csrfTokenMasker;

    @InjectMocks
    private CsrfTokenServiceImpl csrfTokenService;

    @Test
    @DisplayName("UT: onAuthentication() should generate and save token")
    void onAuthentication_shouldGenerateAndSaveToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        CsrfToken csrfToken = new DefaultCsrfToken("X-XSRF-TOKEN", "_csrf", "token");

        when(csrfTokenRepository.generateToken(request)).thenReturn(csrfToken);

        csrfTokenService.onAuthentication(request, response);

        verify(csrfTokenRepository).generateToken(request);
        verify(csrfTokenRepository).saveToken(csrfToken, request, response);
    }

    @Test
    @DisplayName("UT: getMaskedToken() should load, mask and return token")
    void getMaskedToken_shouldLoadMaskAndReturnToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        CsrfToken originalToken = new DefaultCsrfToken("X-XSRF-TOKEN", "_csrf", "original_token");
        String maskedTokenValue = "masked_token";

        when(csrfTokenRepository.loadToken(request)).thenReturn(originalToken);
        when(csrfTokenMasker.mask(originalToken.getToken())).thenReturn(maskedTokenValue);

        CsrfToken maskedToken = csrfTokenService.getMaskedToken(request);

        assertEquals(maskedTokenValue, maskedToken.getToken());
        verify(csrfTokenRepository).loadToken(request);
        verify(csrfTokenMasker).mask(originalToken.getToken());
    }

    @Test
    @DisplayName("UT: getMaskedToken() with null token should throw CsrfTokenNullException")
    void getMaskedToken_nullToken_shouldThrowException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(csrfTokenRepository.loadToken(request)).thenReturn(null);

        assertThrows(CsrfTokenNullException.class, () -> csrfTokenService.getMaskedToken(request));
    }
}
