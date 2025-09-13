package com.specialist.auth.core.web;

import com.specialist.auth.domain.account.models.AccountUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class PostConfirmationAccountLoginOrchestrator implements AccountLoginOrchestrator {

    private final UserDetailsService userDetailsService;
    private final SessionCookieManager sessionCookieManager;

    public PostConfirmationAccountLoginOrchestrator(@Qualifier("accountUserDetailsService") UserDetailsService userDetailsService,
                                                    SessionCookieManager sessionCookieManager) {
        this.userDetailsService = userDetailsService;
        this.sessionCookieManager = sessionCookieManager;
    }

    @Override
    public void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        AccountUserDetails userDetails = (AccountUserDetails) userDetailsService.loadUserByUsername(loginRequest.email());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        sessionCookieManager.create(userDetails, request, response);
    }
}
