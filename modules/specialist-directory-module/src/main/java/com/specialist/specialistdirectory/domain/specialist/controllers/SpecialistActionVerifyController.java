package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistActionFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/specialists")
@RequiredArgsConstructor
public class SpecialistActionVerifyController {

    private final SpecialistActionFacade facade;

    @PostMapping("/recall")
    public ResponseEntity<?> recall(@RequestParam("code") @NotBlank(message = "Code is required.")
                                    @Pattern(regexp = "^\\d{6}$", message = "Invalid code.") String code) {
        facade.recall(code);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasRole('SPECIALIST') && hasAuthority('SPECIALIST_CREATE')")
    @PostMapping("/manage")
    public ResponseEntity<?> manage(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestParam("code") @NotBlank(message = "Code is required.")
                                    @Pattern(regexp = "^\\d{6}$", message = "Invalid code.") String code,
                                    HttpServletRequest request, HttpServletResponse response) {
        facade.manage(principal.getAccountId(), code, request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
