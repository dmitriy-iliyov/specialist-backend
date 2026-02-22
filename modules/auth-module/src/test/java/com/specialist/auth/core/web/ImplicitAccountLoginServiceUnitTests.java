package com.specialist.auth.core.web;

import com.specialist.auth.domain.account.models.AccountUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImplicitAccountLoginServiceUnitTests {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private SessionCookieManager sessionCookieManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private ImplicitAccountLoginService service;

    @Test
    @DisplayName("UT: login() should load user and create session")
    void login_shouldLoadUserAndCreateSession() {
        LoginRequest loginRequest = new LoginRequest("test@test.com", null);
        AccountUserDetails userDetails = mock(AccountUserDetails.class);

        when(userDetailsService.loadUserByUsername("test@test.com")).thenReturn(userDetails);

        service.login(loginRequest, request, response);

        verify(userDetailsService).loadUserByUsername("test@test.com");
        verify(sessionCookieManager).create(userDetails, request, response);
    }

    @Test
    @DisplayName("UT: login() when user not found should throw exception")
    void login_whenUserNotFound_shouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("notfound@test.com", null);
        when(userDetailsService.loadUserByUsername("notfound@test.com")).thenThrow(new UsernameNotFoundException("not found"));

        assertThrows(UsernameNotFoundException.class, () -> service.login(loginRequest, request, response));

        verify(userDetailsService).loadUserByUsername("notfound@test.com");
        verifyNoInteractions(sessionCookieManager);
    }
}
