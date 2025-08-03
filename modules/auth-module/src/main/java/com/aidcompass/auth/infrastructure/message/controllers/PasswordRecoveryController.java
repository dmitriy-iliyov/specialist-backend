package com.aidcompass.auth.infrastructure.message.controllers;

import com.aidcompass.auth.infrastructure.message.models.PasswordRecoveryRequest;
import com.aidcompass.auth.infrastructure.message.services.PasswordRecoveryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts/password-recovery")
@RequiredArgsConstructor
public class PasswordRecoveryController {

    private final PasswordRecoveryService service;

    @PostMapping("/request")
    public ResponseEntity<?> request(@RequestParam("email") @NotBlank(message = "Email is required.") String email) {
        service.sendRecoveryMessage(email);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/recover")
    public ResponseEntity<?> recover(@RequestBody @Valid PasswordRecoveryRequest request) {
        service.recoverPassword(request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
