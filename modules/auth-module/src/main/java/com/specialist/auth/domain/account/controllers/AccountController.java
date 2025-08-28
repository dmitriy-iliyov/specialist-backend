package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.core.AccountAuthService;
import com.specialist.auth.domain.account.models.dtos.AccountEmailUpdateDto;
import com.specialist.auth.domain.account.models.dtos.AccountPasswordUpdateDto;
import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.account.services.EmailUpdateOrchestrator;
import com.specialist.auth.domain.account.services.PersistAccountOrchestrator;
import com.specialist.contracts.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final PersistAccountOrchestrator persistOrchestrator;
    private final AccountService service;
    private final EmailUpdateOrchestrator emailUpdateOrchestrator;
    private final AccountAuthService accountAuthService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid DefaultAccountCreateDto dto, HttpServletResponse response) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(persistOrchestrator.save(dto, response));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/me/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal PrincipalDetails principal,
                                            @RequestBody @Valid AccountPasswordUpdateDto dto) {
        dto.setId(principal.getAccountId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updatePassword(dto));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/me/email")
    public ResponseEntity<?> updateEmail(@AuthenticationPrincipal PrincipalDetails principal,
                                         @RequestBody @Valid AccountEmailUpdateDto dto, HttpServletResponse response) {
        UUID accountId = principal.getAccountId();
        dto.setId(accountId);
        ShortAccountResponseDto responseDto = emailUpdateOrchestrator.updateEmail(dto);
        accountAuthService.logoutFromAll(accountId, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
