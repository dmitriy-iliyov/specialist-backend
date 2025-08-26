package com.specialist.auth.domain.access_token;

import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.exceptions.AccessTokenExpiredException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CookieAccessTokenAuthenticationConverter implements AuthenticationConverter {

    private final AccessTokenDeserializer accessTokenDeserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (request.getCookies() != null) {
            String rawRefreshToken = null;
            for (Cookie cookie: request.getCookies()) {
                if (cookie.getName().equals("__Host-access-token")) {
                    AccessToken accessToken = accessTokenDeserializer.deserialize(cookie.getValue());
                    if (accessToken == null) {
                        throw new AccessTokenExpiredException();
                    }
                    return new PreAuthenticatedAuthenticationToken(accessToken, cookie.getValue());
                }
                if (cookie.getName().equals("__Host-refresh-token")) {
                    rawRefreshToken = cookie.getValue();
                }
            }
            if (rawRefreshToken != null) {
                return new PreAuthenticatedAuthenticationToken(rawRefreshToken, "__Host-refresh-token");
            }
        }
        return null;
    }
}
