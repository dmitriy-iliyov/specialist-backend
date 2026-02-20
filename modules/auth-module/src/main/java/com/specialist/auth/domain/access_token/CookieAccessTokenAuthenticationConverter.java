package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieAccessTokenAuthenticationConverter implements AuthenticationConverter {

    private static final String LOGIN_URI = "/api/auth/login";
    private static final String REFRESH_URI = "/api/auth/refresh";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "__Host-refresh-token";
    private static final String ACCESS_TOKEN_COOKIE_NAME = "__Host-access-token";
    private final AccessTokenDeserializer accessTokenDeserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        String requestUri = request.getRequestURI();
        if (LOGIN_URI.equals(requestUri)) {
            return null;
        }
        if (REFRESH_URI.equals(requestUri)) {
            String rawRefreshToken = null;
            for (Cookie cookie: request.getCookies()) {
                if (cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
                    rawRefreshToken = cookie.getValue();
                }
            }
            if (rawRefreshToken == null) {
                return null;
            }
            return new PreAuthenticatedAuthenticationToken(rawRefreshToken, REFRESH_TOKEN_COOKIE_NAME);
        }
        String rawAccessToken = null;
        for (Cookie cookie: request.getCookies()) {
            if (cookie.getName().equals(ACCESS_TOKEN_COOKIE_NAME)) {
                rawAccessToken = cookie.getValue();
            }
        }
        if (rawAccessToken == null) {
            return null;
        }
        AccessToken accessToken = accessTokenDeserializer.deserialize(rawAccessToken);
        if (accessToken == null) {
            return null;
        }
        return new PreAuthenticatedAuthenticationToken(accessToken, ACCESS_TOKEN_COOKIE_NAME);
    }
}
