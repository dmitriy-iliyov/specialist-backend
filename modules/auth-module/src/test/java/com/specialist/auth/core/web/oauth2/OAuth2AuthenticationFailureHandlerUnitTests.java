package com.specialist.auth.ut.core.web.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.core.web.oauth2.OAuth2AuthenticationFailureHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OAuth2AuthenticationFailureHandlerUnitTests {

    @Mock
    private ObjectMapper mapper;

    private OAuth2AuthenticationFailureHandler failureHandler;

    @BeforeEach
    void setUp() {
        failureHandler = new OAuth2AuthenticationFailureHandler(mapper);
    }

    @Test
    @DisplayName("UT: onAuthenticationFailure should write error to response")
    void onAuthenticationFailure_shouldWriteError() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = new AuthenticationException("Test error") {};
        PrintWriter writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);
        when(mapper.writeValueAsString(anyString())).thenReturn("json_error");

        failureHandler.onAuthenticationFailure(request, response, exception);

        verify(response, times(1)).setStatus(401);
        verify(response, times(1)).setContentType("application/json");
        verify(mapper, times(1)).writeValueAsString(contains("Test error"));
        verify(writer, times(1)).write("json_error");
    }
}
