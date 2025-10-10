package com.specialist.auth.ut.core.web;

import com.specialist.auth.core.web.AccountAuthController;
import com.specialist.auth.core.web.AccountLoginService;
import com.specialist.auth.core.web.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AccountAuthControllerUnitTests {

    @Mock
    private AccountLoginService service;

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
}
