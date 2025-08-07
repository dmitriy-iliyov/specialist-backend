package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.models.enums.UnableReason;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/info/auth/enums")
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
}
