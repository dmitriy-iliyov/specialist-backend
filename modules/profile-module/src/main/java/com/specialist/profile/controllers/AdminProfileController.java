package com.specialist.profile.controllers;

import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.services.ProfileQueryService;
import com.specialist.profile.services.ProfileReadOrchestrator;
import com.specialist.profile.services.SpecialistProfileService;
import com.specialist.utils.validation.annotation.ValidUuid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/profiles")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProfileController {

    private final SpecialistProfileService profileService;
    private final ProfileReadOrchestrator readOrchestrator;
    private final ProfileQueryService queryService;

    public AdminProfileController(SpecialistProfileService profileService, ProfileReadOrchestrator readOrchestrator,
                                  @Qualifier("userProfileQueryService") ProfileQueryService queryService) {
        this.profileService = profileService;
        this.readOrchestrator = readOrchestrator;
        this.queryService = queryService;
    }

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
        profileService.approve(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid ProfileFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryService.findAll(filter));
    }
}
