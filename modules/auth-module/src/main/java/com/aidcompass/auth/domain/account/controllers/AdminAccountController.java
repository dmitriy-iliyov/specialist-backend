package com.aidcompass.auth.domain.account.controllers;

import com.aidcompass.auth.domain.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/accounts")
@RequiredArgsConstructor
public class AdminAccountController {

    private final AccountService service;

    @PostMapping("/{id}/lock")
    public ResponseEntity<?> lock(@PathVariable("id") UUID id) {
        service.lockById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
