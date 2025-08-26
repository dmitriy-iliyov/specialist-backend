package com.specialist.auth.core;

import com.specialist.auth.core.csrf.CsrfTokenService;
import com.specialist.auth.core.models.LoginRequest;
import com.specialist.auth.core.models.Token;
import com.specialist.auth.core.models.TokenType;
import com.specialist.auth.core.oauth2.provider.Provider;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.exceptions.OAuth2RegisteredAttemptedToLocalLoginException;
import com.specialist.auth.exceptions.RefreshTokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class AccountAuthServiceImpl implements AccountAuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final TokenManager tokenManager;
    private final CsrfTokenService csrfTokenService;

    public AccountAuthServiceImpl(@Qualifier("unifiedAccountService") UserDetailsService userDetailsService,
                                  @Qualifier("accountAuthenticationManager") AuthenticationManager authenticationManager,
                                  AccountService accountService, TokenManager tokenManager, CsrfTokenService csrfTokenService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;
        this.tokenManager = tokenManager;
        this.csrfTokenService = csrfTokenService;
    }

    @Override
    public void postConfirmationLogin(String email, HttpServletRequest request, HttpServletResponse response) {
        AccountUserDetails userDetails = (AccountUserDetails) userDetailsService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<TokenType, Token> tokens = tokenManager.generate(userDetails);
        for (Token token: tokens.values()) {
            response.addCookie(AuthCookieFactory.generate(token.rawToken(), token.expiresAt(), token.type()));
        }
        csrfTokenService.onAuthentication(request, response);
    }

    @Override
    public void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        if (!accountService.findProviderByEmail(loginRequest.email()).equals(Provider.LOCAL)) {
            throw new OAuth2RegisteredAttemptedToLocalLoginException();
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AccountUserDetails userDetails = (AccountUserDetails) authentication.getPrincipal();
            Map<TokenType, Token> tokens = tokenManager.generate(userDetails);
            for (Token token: tokens.values()) {
                response.addCookie(AuthCookieFactory.generate(token.rawToken(), token.expiresAt(), token.type()));
            }
            csrfTokenService.onAuthentication(request, response);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            throw e;
        }
    }

    @Override
    public void refresh(UUID refreshTokenId, HttpServletResponse response) {
        try {
            Token token = tokenManager.refresh(refreshTokenId);
            response.addCookie(AuthCookieFactory.generate(token.rawToken(), token.expiresAt(), token.type()));
        } catch (RefreshTokenExpiredException e) {
            response.addCookie(AuthCookieFactory.generateEmpty(TokenType.REFRESH));
            response.addCookie(AuthCookieFactory.generateEmpty(TokenType.ACCESS));
            throw e;
        }
    }

    @Override
    public void logout(UUID refreshTokenId, HttpServletResponse response) {
        tokenManager.deactivate(refreshTokenId);
        cleanCookie(response);
    }

    @Override
    public void logoutFromAll(UUID accountId, HttpServletResponse response) {
        tokenManager.deactivateAll(accountId);
        cleanCookie(response);
    }

    private void cleanCookie(HttpServletResponse response) {
        for (TokenType type: TokenType.values()) {
            response.addCookie(AuthCookieFactory.generateEmpty(type));
        }
        response.addCookie(AuthCookieFactory.generateEmptyCsrf());
    }
}