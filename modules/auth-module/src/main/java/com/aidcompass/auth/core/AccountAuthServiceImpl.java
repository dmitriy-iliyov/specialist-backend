package com.aidcompass.auth.core;

import com.aidcompass.auth.core.csrf.CsrfTokenService;
import com.aidcompass.auth.domain.account.models.AccountUserDetails;
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

import java.util.UUID;

@Service
public class AccountAuthServiceImpl implements AccountAuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final CsrfTokenService csrfTokenService;

    public AccountAuthServiceImpl(@Qualifier("unifiedAccountService") UserDetailsService userDetailsService,
                                  AuthenticationManager authenticationManager, TokenManager tokenManager,
                                  CsrfTokenService csrfTokenService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.csrfTokenService = csrfTokenService;
    }

    @Override
    public void postConfirmationLogin(String email, HttpServletResponse response) {
        AccountUserDetails userDetails = (AccountUserDetails) userDetailsService.loadUserByUsername(email);
        tokenManager.generate(userDetails, response);
    }

    @Override
    public void login(LoginRequest requestDto, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AccountUserDetails userDetails = (AccountUserDetails) authentication.getPrincipal();
            tokenManager.generate(userDetails, response);
            csrfTokenService.onAuthentication(request, response);
        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
        }
    }

    @Override
    public void refresh(UUID refreshTokenId, HttpServletResponse response) {
        tokenManager.refresh(refreshTokenId, response);
    }
}