package com.specialist.user.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.contracts.user.UserType;
import com.specialist.user.models.dtos.UpdateRequest;
import com.specialist.user.services.UserManagementOrchestrator;
import com.specialist.user.services.UserReadOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users/me")
@PreAuthorize("hasAnyRole('USER', 'SPECIALIST')")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementOrchestrator managementOrchestrator;
    private final UserReadOrchestrator readOrchestrator;

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readOrchestrator.findPrivateById(principal.getAccountId(), UserType.fromStringRole(principal.getStringRole())));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestPart("user") String rawDto,
                                    @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(managementOrchestrator.update(
                        new UpdateRequest(principal.getId(), UserType.fromStringRole(principal.getStringRole()), rawDto, avatar)
                ));
    }

    @PatchMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(@AuthenticationPrincipal PrincipalDetails principal,
                                          @RequestPart("avatar") MultipartFile avatar) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(managementOrchestrator.updateAvatar(
                        principal.getAccountId(),
                        UserType.fromStringRole(principal.getStringRole()),
                        avatar)
                );
    }
}