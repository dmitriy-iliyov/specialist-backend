package com.specialist.auth.core.oauth2;

import com.specialist.auth.core.oauth2.provider.Provider;
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

    private final OAuth2AccountAuthService service;

    @PostMapping("/authorize")
    public ResponseEntity<?> authorize(@RequestParam("provider") Provider provider, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.authorize(provider, request));
    }

    @GetMapping("/callback/google")
    public ResponseEntity<?> googleCallback(@ModelAttribute OAuth2QueryParams params, HttpServletRequest request,
                                                  HttpServletResponse response) {
        service.callback(Provider.GOOGLE, params, request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/callback/facebook")
    public ResponseEntity<?> facebookCallback(@ModelAttribute OAuth2QueryParams params, HttpServletRequest request,
                                                    HttpServletResponse response) {
        service.callback(Provider.FACEBOOK, params, request, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
