package com.aidcompass.auth.domain.account.controllers;

import com.aidcompass.auth.domain.account.models.enums.LockReasonType;
import com.aidcompass.auth.domain.account.models.enums.UnableReasonType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/info/enums")
public class AccountInfoController {

    @GetMapping("/unable")
    public ResponseEntity<?> getUnableReasonTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UnableReasonType.values());
    }

    @GetMapping("/lock")
    public ResponseEntity<?> getLockReasonTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(LockReasonType.values());
    }
}
