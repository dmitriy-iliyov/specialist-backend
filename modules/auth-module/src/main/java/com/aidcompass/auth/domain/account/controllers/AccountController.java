package com.aidcompass.auth.domain.account.controllers;

import com.aidcompass.auth.domain.account.models.dtos.AccountCreateDto;
import com.aidcompass.auth.domain.account.services.AccountOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountOrchestrator orchestrator;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid AccountCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }
}
