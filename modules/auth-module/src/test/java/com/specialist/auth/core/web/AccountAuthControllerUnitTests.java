package com.specialist.auth.core.web;

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
import org.springframework.security.authentication.BadCredentialsException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    @DisplayName("UT: login() when service throws exception should throw exception")
    void login_whenServiceThrowsException_shouldThrowException() {
        LoginRequest loginRequest = new LoginRequest("test@test.com", "wrong-pass");
        doThrow(new BadCredentialsException("bad credentials")).when(loginService).login(loginRequest, request, response);

        assertThrows(BadCredentialsException.class, () -> controller.login(loginRequest, request, response));

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
    @DisplayName("UT: refresh() when manager throws exception should throw exception")
    void refresh_whenManagerThrowsException_shouldThrowException() {
        UUID refreshTokenId = UUID.randomUUID();
        RefreshTokenIdHolder principal = () -> refreshTokenId;
        doThrow(new RuntimeException("Refresh failed")).when(sessionCookieManager).refresh(refreshTokenId, response);

        assertThrows(RuntimeException.class, () -> controller.refresh(principal, response));

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
    @DisplayName("UT: logout() when service throws exception should throw exception")
    void logout_whenServiceThrowsException_shouldThrowException() {
        doThrow(new RuntimeException("Logout failed")).when(logoutService).logout(request, response);

        assertThrows(RuntimeException.class, () -> controller.logout(request, response));

        verify(logoutService).logout(request, response);
    }

    @Test
    @DisplayName("UT: logoutFromAll() should call manager and return NO_CONTENT")
    void logoutFromAll_shouldCallManager() {
        UUID accountId = UUID.randomUUID();
        AccessTokenUserDetails principal = mock(AccessTokenUserDetails.class);
        when(principal.getAccountId()).thenReturn(accountId);

        ResponseEntity<?> result = controller.logoutFromAll(principal, response);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(sessionCookieManager).terminateAll(accountId, response);
    }
    
    @Test
    @DisplayName("UT: logoutFromAll() when manager throws exception should throw exception")
    void logoutFromAll_whenManagerThrowsException_shouldThrowException() {
        UUID accountId = UUID.randomUUID();
        AccessTokenUserDetails principal = mock(AccessTokenUserDetails.class);
        when(principal.getAccountId()).thenReturn(accountId);
        doThrow(new RuntimeException("Terminate all failed")).when(sessionCookieManager).terminateAll(accountId, response);

        assertThrows(RuntimeException.class, () -> controller.logoutFromAll(principal, response));

        verify(sessionCookieManager).terminateAll(accountId, response);
    }
}
