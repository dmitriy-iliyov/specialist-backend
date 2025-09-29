package com.specialist.auth.core;

import com.specialist.auth.domain.access_token.AccessTokenFactory;
import com.specialist.auth.domain.access_token.AccessTokenSerializer;
import com.specialist.auth.domain.access_token.models.AccessToken;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.refresh_token.RefreshTokenService;
import com.specialist.auth.domain.refresh_token.models.RefreshToken;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.specialist.auth.exceptions.AccountIdNullException;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import com.specialist.auth.exceptions.RefreshTokenIdNullException;
import com.specialist.auth.exceptions.UserDetailsNullException;
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
        if (userDetails == null) {
            throw new UserDetailsNullException();
        }
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
    public Token generate(ServiceAccountUserDetails userDetails) {
        if (userDetails == null) {
            throw new UserDetailsNullException();
        }
        RefreshToken refreshToken = refreshTokenService.generateAndSave(userDetails);
        AccessToken accessToken = new AccessToken(
                refreshToken.id(),
                refreshToken.accountId(),
                refreshToken.authorities(),
                Instant.now(),
                refreshToken.expiresAt()
        );
        return new Token(TokenType.ACCESS, accessTokenSerializer.serialize(accessToken), accessToken.expiresAt());
    }

    @Override
    public Token refresh(UUID refreshTokenId) {
        if (refreshTokenId == null) {
            throw new RefreshTokenIdNullException();
        }
        RefreshToken refreshToken = refreshTokenService.findById(refreshTokenId);
        if (refreshToken != null) {
            long timeToExpiration = Duration.between(Instant.now(), refreshToken.expiresAt()).toSeconds();
            if (timeToExpiration <= 120) {
                refreshTokenService.deleteById(refreshTokenId);
                throw new RefreshTokenExpiredException();
            } else {
                AccessToken accessToken = accessTokenFactory.generate(refreshToken);
                return new Token(TokenType.ACCESS, accessTokenSerializer.serialize(accessToken), accessToken.expiresAt());
            }
        } else {
            throw new RefreshTokenExpiredException();
        }
    }

    @Override
    public void deactivate(UUID refreshTokenId) {
        if (refreshTokenId == null) {
            throw new RefreshTokenIdNullException();
        }
        refreshTokenService.deleteById(refreshTokenId);
    }

    @Override
    public void deactivateAll(UUID accountId) {
        if (accountId == null) {
            throw new AccountIdNullException();
        }
        refreshTokenService.deleteAllByAccountId(accountId);
    }

    @Override
    public void revoke(UUID refreshTokenId) {
        if (refreshTokenId == null) {
            throw new RefreshTokenIdNullException();
        }
        refreshTokenService.deleteById(refreshTokenId);
    }

    @Override
    public void revokeAll(UUID accountId) {
        if (accountId == null) {
            throw new AccountIdNullException();
        }
        refreshTokenService.deleteAllByAccountId(accountId);
    }
}
