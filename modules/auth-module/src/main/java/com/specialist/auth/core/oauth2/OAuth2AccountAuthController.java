package com.specialist.auth.core.oauth2;

import com.specialist.auth.core.oauth2.models.OAuth2QueryParams;
import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.core.oauth2.services.OAuth2AccountAuthorizeOrchestrator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/oauth2")
@RequiredArgsConstructor
public class OAuth2AccountAuthController {

    private final OAuth2AccountAuthorizeOrchestrator service;

    @PostMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestParam("provider") Provider provider, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.authorize(provider, request));
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam("provider") Provider provider, @ModelAttribute OAuth2QueryParams params,
                                      HttpServletRequest request, HttpServletResponse response) {
        service.callback(provider, params, request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
