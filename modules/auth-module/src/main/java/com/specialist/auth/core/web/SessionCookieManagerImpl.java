package com.specialist.auth.core.web;

import com.specialist.auth.core.Token;
import com.specialist.auth.core.TokenManager;
import com.specialist.auth.core.TokenType;
import com.specialist.auth.core.web.csrf.CsrfTokenService;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionCookieManagerImpl implements SessionCookieManager {

    private final TokenManager tokenManager;
    private final CsrfTokenService csrfTokenService;
    private final CookieManager cookieManager;

    @Override
    public void create(AccountUserDetails userDetails, HttpServletRequest request, HttpServletResponse response) {
        Map<TokenType, Token> tokens = tokenManager.generate(userDetails);
        for (Token token: tokens.values()) {
            response.addCookie(cookieManager.generate(token.rawToken(), token.expiresAt(), token.type().getCookieType()));
        }
        csrfTokenService.onAuthentication(request, response);
    }

    @Override
    public void refresh(UUID refreshTokenId, HttpServletResponse response) {
        try {
            Token token = tokenManager.refresh(refreshTokenId);
            response.addCookie(cookieManager.generate(token.rawToken(), token.expiresAt(), token.type().getCookieType()));
        } catch (RefreshTokenExpiredException e) {
            cookieManager.cleanAll(response);
            throw e;
        }
    }

    @Override
    public void terminate(UUID refreshTokenId, HttpServletResponse response) {
        tokenManager.deactivate(refreshTokenId);
        cookieManager.cleanAll(response);
    }

    @Override
    public void terminateAll(UUID accountId, HttpServletResponse response) {
        tokenManager.deactivateAll(accountId);
        cookieManager.cleanAll(response);
    }
}
