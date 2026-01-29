package com.specialist.auth.ut.core.web;

import com.specialist.auth.core.web.ImplicitAccountLoginService;
import com.specialist.auth.core.web.LoginRequest;
import com.specialist.auth.core.web.SessionCookieManager;
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
}
