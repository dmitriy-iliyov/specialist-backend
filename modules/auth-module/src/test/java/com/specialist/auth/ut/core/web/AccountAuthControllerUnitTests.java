package com.specialist.auth.ut.core.web;

import com.specialist.auth.core.web.*;
import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenIdHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountAuthControllerUnitTests {

    @Mock
    private AccountLoginService loginService;

    @Mock
    private AccountLogoutService logoutService;

    @Mock
    private SessionCookieManager sessionCookieManager;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AccountAuthController controller;

    @Test
    @DisplayName("UT: login() should call service and return NO_CONTENT")
    void login_shouldCallService() {
        LoginRequest loginRequest = new LoginRequest("test@test.com", "pass");

        ResponseEntity<?> result = controller.login(loginRequest, request, response);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(loginService).login(loginRequest, request, response);
    }

    @Test
    @DisplayName("UT: refresh() should call manager and return NO_CONTENT")
    void refresh_shouldCallManager() {
        UUID refreshTokenId = UUID.randomUUID();
        RefreshTokenIdHolder principal = () -> refreshTokenId;

        ResponseEntity<?> result = controller.refresh(principal, response);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(sessionCookieManager).refresh(refreshTokenId, response);
    }

    @Test
    @DisplayName("UT: logout() should call service and return NO_CONTENT")
    void logout_shouldCallService() {
        ResponseEntity<?> result = controller.logout(request, response);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(logoutService).logout(request, response);
    }

    @Test
    @DisplayName("UT: logoutFromAll() should call manager and return NO_CONTENT")
    void logoutFromAll_shouldCallManager() {
        UUID accountId = UUID.randomUUID();
        AccessTokenUserDetails principal = org.mockito.Mockito.mock(AccessTokenUserDetails.class);
        when(principal.getAccountId()).thenReturn(accountId);

        ResponseEntity<?> result = controller.logoutFromAll(principal, response);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(sessionCookieManager).terminateAll(accountId, response);
    }
}
