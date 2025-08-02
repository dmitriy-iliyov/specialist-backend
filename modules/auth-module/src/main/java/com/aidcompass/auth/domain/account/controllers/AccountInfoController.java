package com.aidcompass.auth.domain.account.controllers;

import com.aidcompass.auth.domain.account.models.enums.LockReason;
import com.aidcompass.auth.domain.account.models.enums.UnableReason;
import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.role.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Target;

@RestController
@RequestMapping("/api/v1/auth/info/enums")
public class AccountInfoController {

    @GetMapping("/unable-reasons")
    public ResponseEntity<?> getUnableReasonTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UnableReason.values());
    }

    @GetMapping("/lock-reasons")
    public ResponseEntity<?> getLockReasonTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(LockReason.values());
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Role.values());
    }

    @GetMapping("/authorities")
    public ResponseEntity<?> getAuthorities() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Authority.values());
    }
}
