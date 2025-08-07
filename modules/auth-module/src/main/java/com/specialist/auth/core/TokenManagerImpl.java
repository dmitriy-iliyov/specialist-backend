package com.specialist.auth.core;

import com.specialist.auth.core.cookie.AuthCookieFactory;
import com.specialist.auth.core.cookie.TokenType;
import com.specialist.auth.domain.access_token.AccessTokenFactory;
import com.specialist.auth.domain.access_token.AccessTokenSerializer;
import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenStatus;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenManagerImpl implements TokenManager {

    private final RefreshTokenService refreshTokenService;
    private final AccessTokenFactory accessTokenFactory;
    private final AccessTokenSerializer accessTokenSerializer;

    @Override
    public void generate(AccountUserDetails userDetails, HttpServletResponse response) {
        RefreshToken refreshToken = refreshTokenService.generateAndSave(userDetails);
        AccessToken accessToken = accessTokenFactory.generate(refreshToken);
        String rawRefreshToken = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(refreshToken.id().toString().getBytes(StandardCharsets.UTF_8));
        String rawAccessToken = accessTokenSerializer.serialize(accessToken);
        response.addCookie(AuthCookieFactory.generate(rawRefreshToken, refreshToken.expiresAt(), TokenType.REFRESH));
        response.addCookie(AuthCookieFactory.generate(rawAccessToken, accessToken.expiresAt(), TokenType.ACCESS));
    }

    @Override
    public Map<String, String> generate(ServiceAccountUserDetails userDetails) {
        RefreshToken refreshToken = refreshTokenService.generateAndSave(userDetails);
        AccessToken accessToken = new AccessToken(
                refreshToken.id(),
                refreshToken.subjectId(),
                refreshToken.authorities(),
                Instant.now(),
                refreshToken.expiresAt()
        );
        return Map.of("access_token", accessTokenSerializer.serialize(accessToken));
    }

    @Override
    public void refresh(UUID refreshTokenId, HttpServletResponse response) {
        RefreshToken refreshToken = refreshTokenService.findById(refreshTokenId);
        if (refreshToken.status().equals(RefreshTokenStatus.ACTIVE)) {
            long timeToExpiration = Duration.between(refreshToken.expiresAt(), Instant.now()).getSeconds();
            if (timeToExpiration <= 120) {
                refreshTokenService.deactivateById(refreshTokenId);
                response.addCookie(AuthCookieFactory.generateEmpty(TokenType.REFRESH));
                response.addCookie(AuthCookieFactory.generateEmpty(TokenType.ACCESS));
                throw new RefreshTokenExpiredException();
            } else {
                AccessToken accessToken = accessTokenFactory.generate(refreshToken);
                response.addCookie(AuthCookieFactory.generate(
                        accessTokenSerializer.serialize(accessToken), accessToken.expiresAt(), TokenType.ACCESS)
                );
            }
        } else {
            throw new RefreshTokenExpiredException();
        }
    }
}
