package com.specialist.profile.controllers;

import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.profile.services.ProfileReadService;
import com.specialist.profile.services.SpecialistProfileService;
import com.specialist.utils.uuid.UUIDv7;
import jakarta.validation.Valid;
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
    private final ProfileReadService profileReadService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @UUIDv7(paramName = "id", message = "Id should have valid format.") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(profileReadService.findPrivateById(UUID.fromString(id)));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approveSpecialist(@PathVariable("id") @UUIDv7(paramName = "id", message = "Id should have valid format.") String id) {
        specialistProfileService.approve(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid ProfileFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(profileReadService.findAll(ScopeType.PRIVATE, filter));
    }
}
