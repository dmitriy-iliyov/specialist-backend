package com.specialist.auth.ut.core.api;

import com.specialist.auth.core.api.ServiceAccountAuthController;
import com.specialist.auth.core.api.ServiceAccountLoginService;
import com.specialist.auth.core.api.ServiceLoginRequest;
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
    ServiceAccountLoginService service;

    @InjectMocks
    ServiceAccountAuthController controller;

    @Test
    @DisplayName("UT: login() should call service.login() and return 204 NO_CONTENT")
    public void login_shouldCallServiceAndReturnNoContent() {
        ServiceLoginRequest requestDto = new ServiceLoginRequest("client-accountId", "client-secret");

        ResponseEntity<?> response = controller.login(requestDto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());

        verify(service, times(1)).login(requestDto);
        verifyNoMoreInteractions(service);
    }
}
