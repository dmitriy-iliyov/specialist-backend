package com.specialist.auth.it.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.core.api.BearerAuthenticationEntryPoint;
import com.specialist.auth.exceptions.InvalidJwtSignatureException;
import com.specialist.auth.exceptions.JwtParseException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class BearerAuthenticationEntryPointIntegrationTests {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    BearerAuthenticationEntryPoint tested;

    final String message = "Test exception message.";
    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();
    }

    @Test
    @DisplayName("IT: commence() when exception class is InvalidJwtSignatureException.class should return message in application/json with 400 status code")
    void commence_whenInvalidJwtSignatureException_shouldReturn400AndMessage() throws IOException {
        InvalidJwtSignatureException e = new InvalidJwtSignatureException(new RuntimeException(message));

        tested.commence(request, response, e);

        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        assertEquals("application/json", response.getContentType());
        String currentMessage = (String) mapper.readValue(response.getContentAsString(), HashMap.class).get("message");
        assertEquals(message, currentMessage);
    }

    @Test
    @DisplayName("IT: commence() when exception class is JwtParseException.class should return message in application/json with 500 status code")
    void commence_whenJwtParseException_shouldReturn500AndMessage() throws IOException {
        JwtParseException e = new JwtParseException(new RuntimeException(message));

        tested.commence(request, response, e);

        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
        assertEquals("application/json", response.getContentType());
        String currentMessage = (String) mapper.readValue(response.getContentAsString(), HashMap.class).get("message");
        assertEquals(message, currentMessage);
    }

    @Test
    @DisplayName("IT: commence() when exception class is AuthenticationException.class should return message in application/json with 401 status code")
    void commence_whenAuthenticationException_shouldReturn401AndMessage() throws IOException {
        BadCredentialsException e = new BadCredentialsException(message);

        tested.commence(request, response, e);

        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("application/json", response.getContentType());
        String currentMessage = (String) mapper.readValue(response.getContentAsString(), HashMap.class).get("message");
        assertEquals(message, currentMessage);
    }
}
