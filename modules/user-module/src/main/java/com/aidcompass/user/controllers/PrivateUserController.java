package com.aidcompass.user.controllers;

import com.aidcompass.contracts.auth.PrincipalDetails;
import com.aidcompass.user.models.dto.UserCreateDto;
import com.aidcompass.user.models.dto.UserUpdateDto;
import com.aidcompass.user.services.UserOrchestrator;
import com.aidcompass.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/users/me")
@RequiredArgsConstructor
public class PrivateUserController {

    private final UserOrchestrator orchestrator;
    private final UserService service;


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestPart("user") @Valid UserCreateDto dto,
                                    @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        dto.setId(principal.getUserId());
        dto.setAvatar(avatar);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateById(principal.getUserId()));
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestPart("user") @Valid UserUpdateDto dto,
                                    @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        dto.setId(principal.getUserId());
        dto.setAvatar(avatar);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal) {
        orchestrator.delete(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}