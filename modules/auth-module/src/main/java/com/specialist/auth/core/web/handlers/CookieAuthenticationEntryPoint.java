package com.specialist.auth.core.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.core.CookieManager;
import com.specialist.auth.core.models.TokenType;
import com.specialist.auth.exceptions.AccessTokenExpiredException;
import com.specialist.auth.exceptions.InvalidJwtSignatureException;
import com.specialist.auth.exceptions.JwtParseException;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
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
public class CookieAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final CookieManager cookieManager;
    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof RefreshTokenExpiredException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            cookieManager.cleanAll(response);
        } else if (authException instanceof AccessTokenExpiredException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addCookie(cookieManager.clean(TokenType.ACCESS.getCookieType()));
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
