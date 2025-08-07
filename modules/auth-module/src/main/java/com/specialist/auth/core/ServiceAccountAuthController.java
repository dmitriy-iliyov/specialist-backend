package com.specialist.auth.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system/v1/auth")
@RequiredArgsConstructor
public class ServiceAccountAuthController {

    private final ServiceAccountAuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid ServiceLoginRequest requestDto, HttpServletRequest request) {
        service.login(requestDto, request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
