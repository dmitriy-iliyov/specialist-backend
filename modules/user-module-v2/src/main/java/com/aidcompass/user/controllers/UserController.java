package com.aidcompass.user.controllers;

import com.aidcompass.user.PrincipalDetails;
import com.aidcompass.user.models.dto.UserCreateDto;
import com.aidcompass.user.models.dto.UserUpdateDto;
import com.aidcompass.user.services.UserOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/users")
@RequiredArgsConstructor
public class UserController {

    private final UserOrchestrator orchestrator;


    @PostMapping("/me")
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid UserCreateDto dto) {
        dto.setId(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body();
    }

    @GetMapping("/me")
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body();
    }

    @PutMapping("/me")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid UserUpdateDto dto) {
        dto.setId(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body();
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body();
    }
}
