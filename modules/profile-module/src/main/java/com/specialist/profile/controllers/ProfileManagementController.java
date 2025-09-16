package com.specialist.profile.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.models.dtos.UpdateRequest;
import com.specialist.profile.services.ProfileManagementOrchestrator;
import com.specialist.profile.services.ProfileReadOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/profiles/me")
@PreAuthorize("hasAnyRole('USER', 'SPECIALIST')")
@RequiredArgsConstructor
public class ProfileManagementController {

    private final ProfileManagementOrchestrator managementOrchestrator;
    private final ProfileReadOrchestrator readOrchestrator;

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readOrchestrator.findPrivateById(principal.getAccountId(), ProfileType.fromStringRole(principal.getStringRole())));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestPart("profile") String rawDto,
                                    @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(managementOrchestrator.update(
                        new UpdateRequest(principal.getId(), ProfileType.fromStringRole(principal.getStringRole()), rawDto, avatar)
                ));
    }

    @PatchMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(@AuthenticationPrincipal PrincipalDetails principal,
                                          @RequestPart("avatar") MultipartFile avatar) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(managementOrchestrator.updateAvatar(
                        principal.getAccountId(),
                        ProfileType.fromStringRole(principal.getStringRole()),
                        avatar)
                );
    }
}