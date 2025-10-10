package com.specialist.auth.ut.core.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.core.api.BearerAuthenticationEntryPoint;
import com.specialist.auth.exceptions.InvalidJwtSignatureException;
import com.specialist.auth.exceptions.JwtParseException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BearerAuthenticationEntryPointUnitTests {

    ObjectMapper mapper;
    BearerAuthenticationEntryPoint tested;
    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        this.mapper = new ObjectMapper();
        this.tested = new BearerAuthenticationEntryPoint(mapper);
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();
    }

    @Test
    @DisplayName("UT: commence() when exception is InvalidJwtSignatureException should return 400 and JSON message")
    void commence_whenInvalidJwtSignatureException_shouldReturn400AndMessage() throws IOException {
        String message = "Invalid jwt signature.";
        InvalidJwtSignatureException e = new InvalidJwtSignatureException(new RuntimeException(message));

        tested.commence(request, response, e);

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        String currentMessage = (String) mapper.readValue(response.getContentAsString(), HashMap.class).get("message");
        assertEquals(message, currentMessage);
    }

    @Test
    @DisplayName("UT: commence() when exception is JwtParseException should return 500 and JSON message")
    void commence_whenJwtParseException_shouldReturn500AndMessage() throws IOException {
        String message = "Jwt parse exception.";
        JwtParseException e = new JwtParseException(new RuntimeException());

        tested.commence(request, response, e);

        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        String currentMessage = (String) mapper.readValue(response.getContentAsString(), HashMap.class).get("message");
        assertEquals(message, currentMessage);
    }

    @Test
    @DisplayName("UT: commence() when exception is AuthenticationException should return 401 and JSON message")
    void commence_whenAuthenticationException_shouldReturn401AndMessage() throws IOException {
        String message = "Test exception message.";
        BadCredentialsException e = new BadCredentialsException(message);

        tested.commence(request, response, e);

        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        String currentMessage = (String) mapper.readValue(response.getContentAsString(), HashMap.class).get("message");
        assertEquals(message, currentMessage);
    }
}
