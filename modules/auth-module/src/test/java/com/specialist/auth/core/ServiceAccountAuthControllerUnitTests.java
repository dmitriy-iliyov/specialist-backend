package com.specialist.auth.core;

import com.specialist.auth.core.models.ServiceLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceAccountAuthControllerUnitTests {

    @Mock
    ServiceAccountAuthService service;

    @Mock
    HttpServletRequest httpServletRequest;

    @InjectMocks
    ServiceAccountAuthController controller;

    @Test
    @DisplayName("UT: login() should call service.login() and return 204 NO_CONTENT")
    public void login_shouldCallServiceAndReturnNoContent() {
        ServiceLoginRequest requestDto = new ServiceLoginRequest("client-id", "client-secret");

        ResponseEntity<?> response = controller.login(requestDto, httpServletRequest);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());

        verify(service, times(1)).login(requestDto, httpServletRequest);
        verifyNoMoreInteractions(service);
    }
}
