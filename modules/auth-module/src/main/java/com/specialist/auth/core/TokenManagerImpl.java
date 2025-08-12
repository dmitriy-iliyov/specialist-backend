package com.specialist.auth.core;

import com.specialist.auth.core.models.Token;
import com.specialist.auth.core.models.TokenType;
import com.specialist.auth.domain.access_token.AccessTokenFactory;
import com.specialist.auth.domain.access_token.AccessTokenSerializer;
import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenStatus;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
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
    public Map<TokenType, Token> generate(AccountUserDetails userDetails) {
        RefreshToken refreshToken = refreshTokenService.generateAndSave(userDetails);
        AccessToken accessToken = accessTokenFactory.generate(refreshToken);
        String rawRefreshToken = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(refreshToken.id().toString().getBytes(StandardCharsets.UTF_8));
        String rawAccessToken = accessTokenSerializer.serialize(accessToken);
        return Map.of(
                TokenType.REFRESH, new Token(TokenType.REFRESH, rawRefreshToken, refreshToken.expiresAt()),
                TokenType.ACCESS, new Token(TokenType.ACCESS, rawAccessToken, accessToken.expiresAt())
        );
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
    public Token refresh(UUID refreshTokenId) {
        RefreshToken refreshToken = refreshTokenService.findById(refreshTokenId);
        if (refreshToken.status().equals(RefreshTokenStatus.ACTIVE)) {
            long timeToExpiration = Duration.between(refreshToken.expiresAt(), Instant.now()).getSeconds();
            if (timeToExpiration <= 120) {
                refreshTokenService.deactivateById(refreshTokenId);
                throw new RefreshTokenExpiredException();
            } else {
                AccessToken accessToken = accessTokenFactory.generate(refreshToken);
                return new Token(TokenType.ACCESS, accessTokenSerializer.serialize(accessToken), accessToken.expiresAt());
            }
        } else {
            throw new RefreshTokenExpiredException();
        }
    }
}
