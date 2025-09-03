package com.specialist.auth.core;

import com.specialist.auth.core.models.LoginRequest;
import com.specialist.auth.core.oauth2.provider.Provider;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.exceptions.OAuth2RegisteredAttemptedToLocalLoginException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DefaultAccountLoginOrchestrator implements AccountLoginOrchestrator {

    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final SessionCookieManager sessionCookieManager;

    public DefaultAccountLoginOrchestrator(@Qualifier("accountAuthenticationManager") AuthenticationManager authenticationManager,
                                           AccountService accountService, SessionCookieManager sessionCookieManager) {
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;
        this.sessionCookieManager = sessionCookieManager;
    }

    @Override
    public void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        if (!accountService.findProviderByEmail(loginRequest.email()).equals(Provider.LOCAL)) {
            throw new OAuth2RegisteredAttemptedToLocalLoginException();
        }
        AccountUserDetails userDetails;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userDetails = (AccountUserDetails) authentication.getPrincipal();
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            throw e;
        }
        sessionCookieManager.create(userDetails, request, response);
    }
}