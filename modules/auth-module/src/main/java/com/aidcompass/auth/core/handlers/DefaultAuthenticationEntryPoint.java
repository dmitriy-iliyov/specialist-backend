package com.aidcompass.auth.core.handlers;

import com.aidcompass.auth.core.cookie.TokenType;
import com.aidcompass.auth.exceptions.AccessTokenExpiredException;
import com.aidcompass.auth.exceptions.InvalidJwtSignatureException;
import com.aidcompass.auth.exceptions.JwtParseException;
import com.aidcompass.auth.exceptions.RefreshTokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthCookieFactory authCookieFactory;
    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof RefreshTokenExpiredException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addCookie(authCookieFactory.generateEmpty(TokenType.REFRESH));
            response.addCookie(authCookieFactory.generateEmpty(TokenType.ACCESS));
        } else if (authException instanceof AccessTokenExpiredException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addCookie(authCookieFactory.generateEmpty(TokenType.ACCESS));
        } else if (authException instanceof InvalidJwtSignatureException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (authException instanceof JwtParseException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Jwt parse exception", authException);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        Map<String, String> body = new HashMap<>(Map.of("message", authException.getMessage()));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(body));
    }
}
