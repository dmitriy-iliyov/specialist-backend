package com.specialist.auth.core;

import com.specialist.auth.core.models.LoginRequest;
import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AccountAuthController {

    private final AccountAuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest requestDto, HttpServletRequest request,
                                   HttpServletResponse response) {
        service.login(requestDto, request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyRole({'USER', 'ADMIN'})")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@AuthenticationPrincipal AccessTokenUserDetails principal,
                                     HttpServletResponse response) {
        service.refresh(principal.getId(), response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
