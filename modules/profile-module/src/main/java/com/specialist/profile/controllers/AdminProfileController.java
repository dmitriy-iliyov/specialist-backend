package com.specialist.profile.controllers;

import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.profile.services.ProfileReadOrchestrator;
import com.specialist.profile.services.SpecialistProfileService;
import com.specialist.utils.validation.annotation.ValidUuid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/profiles")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminProfileController {

    private final SpecialistProfileService specialistProfileService;
    private final ProfileReadOrchestrator readOrchestrator;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @NotNull(message = "Id is required.")
                                     @ValidUuid(paramName = "id") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readOrchestrator.findPrivateById(UUID.fromString(id)));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approveSpecialist(@PathVariable("id") @NotNull(message = "Id is required.")
                                               @ValidUuid(paramName = "id") String id) {
        specialistProfileService.approve(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid ProfileFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readOrchestrator.findAll(ScopeType.PRIVATE, filter));
    }
}
