package com.specialist.auth.core.web.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.auth.core.TokenType;
import com.specialist.auth.core.web.CookieManager;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessTokenAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final CookieManager cookieManager;
    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        if (exception instanceof RefreshTokenExpiredException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            cookieManager.cleanAll(response);
        } else if (exception instanceof AccessTokenExpiredException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addCookie(cookieManager.clean(TokenType.ACCESS.getCookieType()));
        } else if (exception instanceof InvalidJwtSignatureException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else if (exception instanceof JwtParseException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Jwt parse exception", exception);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        Map<String, String> body = new HashMap<>(Map.of("message", exception.getMessage()));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(body));
    }
}
