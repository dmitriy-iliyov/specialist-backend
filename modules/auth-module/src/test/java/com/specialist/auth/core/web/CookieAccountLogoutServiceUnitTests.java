package com.specialist.auth.core.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CookieAccountLogoutServiceUnitTests {

    @Mock
    private LogoutHandler sessionCookieLogoutHandler;

    @Mock
    private LogoutSuccessHandler logoutSuccessHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private CookieAccountLogoutService service;

    @Test
    @DisplayName("UT: logout() should call handlers")
    void logout_shouldCallHandlers() throws ServletException, IOException {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        service.logout(request, response);

        verify(sessionCookieLogoutHandler).logout(request, response, authentication);
        verify(logoutSuccessHandler).onLogoutSuccess(request, response, authentication);
    }

    @Test
    @DisplayName("UT: logout() when success handler throws exception should rethrow runtime exception")
    void logout_whenSuccessHandlerThrows_shouldRethrow() throws ServletException, IOException {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        doThrow(new IOException("Error")).when(logoutSuccessHandler).onLogoutSuccess(request, response, authentication);

        assertThrows(RuntimeException.class, () -> service.logout(request, response));

        verify(sessionCookieLogoutHandler).logout(request, response, authentication);
    }
}
