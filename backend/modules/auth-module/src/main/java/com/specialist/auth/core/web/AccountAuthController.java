package com.specialist.auth.core.web;

import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import com.specialist.auth.domain.refresh_token.models.RefreshTokenIdHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AccountAuthController {

    private final AccountLoginService loginService;
    private final AccountLogoutService logoutService;
    private final SessionCookieManager sessionCookieManager;

    public AccountAuthController(@Qualifier("defaultAccountLoginService") AccountLoginService loginService,
                                 AccountLogoutService logoutService, SessionCookieManager sessionCookieManager) {
        this.loginService = loginService;
        this.logoutService = logoutService;
        this.sessionCookieManager = sessionCookieManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest requestDto, HttpServletRequest request,
                                   HttpServletResponse response) {
        loginService.login(requestDto, request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'SPECIALIST')")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@AuthenticationPrincipal RefreshTokenIdHolder principal,
                                     HttpServletResponse response) {
        sessionCookieManager.refresh(principal.getId(), response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'SPECIALIST')")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logoutService.logout(request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'SPECIALIST')")
    @PostMapping("/logout/all")
    public ResponseEntity<?> logoutFromAll(@AuthenticationPrincipal AccessTokenUserDetails principal,
                                           HttpServletResponse response) {
        sessionCookieManager.terminateAll(principal.getAccountId(), response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}