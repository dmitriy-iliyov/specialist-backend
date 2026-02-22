package com.specialist.auth.core.web.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccessTokenAuthenticationSuccessHandlerUnitTests {

    @InjectMocks
    private AccessTokenAuthenticationSuccessHandler successHandler;

    @Test
    @DisplayName("UT: onAuthenticationSuccess() should set authentication in context and set OK status")
    void onAuthenticationSuccess_shouldSetAuthenticationAndStatus() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        successHandler.onAuthenticationSuccess(request, response, authentication);

        assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}
