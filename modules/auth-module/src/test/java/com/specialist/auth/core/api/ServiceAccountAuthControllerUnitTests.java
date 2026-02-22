package com.specialist.auth.core.api;

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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ServiceAccountAuthControllerUnitTests {

    @Mock
    private ServiceAccountLoginService loginService;

    @InjectMocks
    private ServiceAccountAuthController controller;

    @Test
    @DisplayName("UT: login() should call service and return NO_CONTENT")
    void login_shouldCallService() {
        ServiceLoginRequest request = new ServiceLoginRequest(UUID.randomUUID().toString(), "secret");

        ResponseEntity<?> result = controller.login(request);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(loginService).login(request);
        verifyNoMoreInteractions(loginService);
    }

    @Test
    @DisplayName("UT: login() when service throws exception should throw exception")
    void login_whenServiceThrowsException_shouldThrowException() {
        ServiceLoginRequest request = new ServiceLoginRequest(UUID.randomUUID().toString(), "wrong-secret");
        doThrow(new BadCredentialsException("bad credentials")).when(loginService).login(request);

        assertThrows(BadCredentialsException.class, () -> controller.login(request));

        verify(loginService).login(request);
        verifyNoMoreInteractions(loginService);
    }
}
