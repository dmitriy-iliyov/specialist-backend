package com.aidcompass.auth.domain.account.controllers;

import com.aidcompass.auth.domain.account.models.dtos.AccountUpdateDto;
import com.aidcompass.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.aidcompass.auth.domain.account.services.AccountService;
import com.aidcompass.auth.domain.account.services.PersistAccountOrchestrator;
import com.aidcompass.contracts.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final PersistAccountOrchestrator orchestrator;
    private final AccountService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DefaultAccountCreateDto dto, HttpServletResponse response) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto, response));
    }

    @PatchMapping("/me")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid AccountUpdateDto dto) {
        dto.setId(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(dto));
    }
}
