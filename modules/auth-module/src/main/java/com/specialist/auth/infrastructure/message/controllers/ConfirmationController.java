package com.specialist.auth.infrastructure.message.controllers;

import com.specialist.auth.core.AccountAuthService;
import com.specialist.auth.infrastructure.message.services.ConfirmationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts/confirmation")
@RequiredArgsConstructor
public class ConfirmationController {

    private final ConfirmationService confirmationService;
    private final AccountAuthService accountAuthService;

    @PostMapping("/request")
    public ResponseEntity<?> request(@RequestParam("email") @NotBlank(message = "Email is required.") String email) {
        confirmationService.sendConfirmationCode(email);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam("code") @NotBlank(message = "Code is required.")
                                     @Pattern(regexp = "^\\d{6}$", message = "Invalid code.") String code,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        accountAuthService.postConfirmationLogin(confirmationService.confirmEmailByCode(code), request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
