package com.specialist.auth.core;

import com.specialist.auth.core.models.LoginRequest;
import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import com.specialist.contracts.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AccountAuthController {

    private final AccountLoginOrchestrator orchestrator;
    private final SessionCookieManager sessionCookieManager;

    public AccountAuthController(@Qualifier("defaultAccountLoginOrchestrator") AccountLoginOrchestrator orchestrator,
                                 SessionCookieManager sessionCookieManager) {
        this.orchestrator = orchestrator;
        this.sessionCookieManager = sessionCookieManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest requestDto, HttpServletRequest request,
                                   HttpServletResponse response) {
        orchestrator.login(requestDto, request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    /*
    * @AuthenticationPrincipal can be AccessTokenUserDetails.class or RefreshTokenUserDetails.class
    */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@AuthenticationPrincipal PrincipalDetails principal,
                                     HttpServletResponse response) {
        sessionCookieManager.refresh(principal.getId(), response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/logout/all")
    public ResponseEntity<?> logoutFromAll(@AuthenticationPrincipal AccessTokenUserDetails principal,
                                           HttpServletResponse response) {
        sessionCookieManager.terminateAll(principal.getAccountId(), response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
