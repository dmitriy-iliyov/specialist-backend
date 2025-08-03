package com.aidcompass.auth.infrastructure.message.controllers;

import com.aidcompass.auth.infrastructure.message.services.ConfirmationService;
import com.aidcompass.contracts.auth.PrincipalDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts/confirmation")
@RequiredArgsConstructor
public class ConfirmationController {

    private final ConfirmationService service;

    @PostMapping("/request")
    public ResponseEntity<?> request(@AuthenticationPrincipal PrincipalDetails principal,
                                     @RequestParam("email") @NotBlank(message = "Email is required.") String email) {
        service.sendConfirmationMessage(principal.getUserId(), email);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam("code") @NotBlank(message = "Code is required.")
                                     @Pattern(regexp = "^//d{6}$", message = "Invalid code.") String code) {
        service.confirmEmail(code);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
