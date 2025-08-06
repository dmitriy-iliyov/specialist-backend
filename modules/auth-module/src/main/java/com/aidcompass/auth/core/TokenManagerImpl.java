package com.aidcompass.auth.core;

import com.aidcompass.auth.core.cookie.AuthCookieFactory;
import com.aidcompass.auth.core.cookie.TokenType;
import com.aidcompass.auth.domain.access_token.AccessTokenFactory;
import com.aidcompass.auth.domain.access_token.models.AccessToken;
import com.aidcompass.auth.domain.account.models.AccountUserDetails;
import com.aidcompass.auth.domain.refresh_token.RefreshTokenService;
import com.aidcompass.auth.domain.refresh_token.models.RefreshToken;
import com.aidcompass.auth.domain.refresh_token.models.RefreshTokenStatus;
import com.aidcompass.auth.exceptions.RefreshTokenExpiredException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenManagerImpl implements TokenManager {

    private final RefreshTokenService refreshTokenService;
    private final AccessTokenFactory accessTokenFactory;
    private final AuthCookieFactory authCookieFactory;

    @Override
    public void generate(AccountUserDetails userDetails, HttpServletResponse response) {
        RefreshToken refreshToken = refreshTokenService.generateAndSave(userDetails);
        AccessToken accessToken = accessTokenFactory.generate(refreshToken);
        response.addCookie(authCookieFactory.generate(refreshToken));
        response.addCookie(authCookieFactory.generate(accessToken));
    }

    @Override
    public void refresh(UUID refreshTokenId, HttpServletResponse response) {
        RefreshToken refreshToken = refreshTokenService.findById(refreshTokenId);
        if (refreshToken.status().equals(RefreshTokenStatus.ACTIVE)) {
            long timeToExpiration = Duration.between(refreshToken.expiresAt(), Instant.now()).getSeconds();
            if (timeToExpiration <= 120) {
                refreshTokenService.deactivateById(refreshTokenId);
                response.addCookie(authCookieFactory.generateEmpty(TokenType.REFRESH));
                response.addCookie(authCookieFactory.generateEmpty(TokenType.ACCESS));
                throw new RefreshTokenExpiredException();
            } else {
                AccessToken accessToken = accessTokenFactory.generate(refreshToken);
                response.addCookie(authCookieFactory.generate(accessToken));
            }
        } else {
            throw new RefreshTokenExpiredException();
        }
    }
}
