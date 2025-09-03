package com.specialist.auth.core;

import com.specialist.auth.core.models.LoginRequest;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountAuthControllerUnitTests {

    @Mock
    private AccountLoginOrchestrator service;

    @Mock
    private HttpServletRequest httpRequest;

    @Mock
    private HttpServletResponse httpResponse;

    @InjectMocks
    private AccountAuthController controller;

    @Test
    void login_shouldReturnNoContent() {
        LoginRequest request = new LoginRequest("test@example.com", "password");

        ResponseEntity<?> response = controller.login(request, httpRequest, httpResponse);

        verify(service).login(request, httpRequest, httpResponse);
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(204));
        verifyNoMoreInteractions(service);
    }

    @Test
    void refresh_shouldReturnNoContent() {
        RefreshTokenUserDetails principal = mock(RefreshTokenUserDetails.class);
        UUID id = UUID.randomUUID();
        when(principal.getId()).thenReturn(id);

        ResponseEntity<?> response = controller.refresh(principal, httpResponse);

        verify(service).refresh(id, httpResponse);
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(204));
        verifyNoMoreInteractions(service);
    }
}
