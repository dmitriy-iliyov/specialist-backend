package com.specialist.auth.ut.core.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.core.api.BearerAuthenticationEntryPoint;
import com.specialist.auth.exceptions.InvalidJwtSignatureException;
import com.specialist.auth.exceptions.JwtParseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BearerAuthenticationEntryPointUnitTests {

    @Mock
    private ObjectMapper mapper;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private BearerAuthenticationEntryPoint entryPoint;

    @Test
    @DisplayName("UT: commence() when InvalidJwtSignatureException should return 400")
    void commence_whenInvalidJwtSignature_shouldReturn400() throws IOException {
        AuthenticationException exception = new InvalidJwtSignatureException(new Exception());
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        when(mapper.writeValueAsString(any())).thenReturn("json");

        entryPoint.commence(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response).setContentType("application/json");
        verify(response.getWriter()).write("json");
    }

    @Test
    @DisplayName("UT: commence() when JwtParseException should return 500 and log error")
    void commence_whenJwtParseException_shouldReturn500AndMessage() throws IOException {
        AuthenticationException exception = new JwtParseException(new Exception());
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        when(mapper.writeValueAsString(any())).thenReturn("json");

        entryPoint.commence(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        verify(response).setContentType("application/json");
        verify(response.getWriter()).write("json");
    }

    @Test
    @DisplayName("UT: commence() when other exception should return 401")
    void commence_whenOtherException_shouldReturn401() throws IOException {
        AuthenticationException exception = new AuthenticationException("Auth failed") {};
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
        when(mapper.writeValueAsString(any())).thenReturn("json");

        entryPoint.commence(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");
        verify(response.getWriter()).write("json");
    }
}
