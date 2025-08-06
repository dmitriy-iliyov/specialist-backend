package com.aidcompass.auth.core.cookie;

import com.aidcompass.auth.domain.access_token.AccessTokenSerializer;
import com.aidcompass.auth.domain.access_token.models.AccessToken;
import com.aidcompass.auth.domain.refresh_token.models.RefreshToken;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AuthCookieFactoryImpl implements AuthCookieFactory {

    private final AccessTokenSerializer accessTokenSerializer;

    @Override
    public Cookie generate(RefreshToken refreshToken) {
        String serializedId = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(refreshToken.id().toString().getBytes(StandardCharsets.UTF_8));
        Cookie cookie = new Cookie("__Host-refresh-token", serializedId);
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) Duration.between(Instant.now(), refreshToken.expiresAt()).getSeconds());
        return cookie;
    }

    @Override
    public Cookie generate(AccessToken accessToken) {
        String serializedAccessToken = accessTokenSerializer.serialize(accessToken);
        Cookie cookie = new Cookie("__Host-access-token", serializedAccessToken);
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) Duration.between(Instant.now(), accessToken.expiresAt()).getSeconds());
        return cookie;
    }

    @Override
    public Cookie generateEmpty(TokenType type) {
        Cookie cookie;
        if (type.equals(TokenType.REFRESH)) {
            cookie = new Cookie("__Host-refresh-token", "");
        } else {
            cookie = new Cookie("__Host-access-token", "");
        }
        cookie.setPath("/");
        cookie.setDomain(null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        return cookie;
    }
}
