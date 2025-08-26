package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/v1/auth")
@PreAuthorize("hasRole('ADMIN') && hasAnyAuthority({'ACCOUNT_CREATE', 'SERVICE_ACCOUNT_MANAGER'})")
public class AdminAccountInfoController {

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

    @PreAuthorize("hasAuthority('ACCOUNT_UNABLE')")
    @GetMapping("/disable-reasons")
    public ResponseEntity<?> getDisableReasonTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(DisableReason.values());
    }

    @PreAuthorize("hasAuthority('ACCOUNT_LOCK')")
    @GetMapping("/lock-reasons")
    public ResponseEntity<?> getLockReasonTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(LockReason.values());
    }
}
