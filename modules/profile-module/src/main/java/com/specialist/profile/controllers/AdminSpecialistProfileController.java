package com.specialist.profile.controllers;

import com.specialist.profile.services.SpecialistProfileService;
import com.specialist.utils.validation.annotation.ValidUuid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/profiles/specialists")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminSpecialistProfileController {

    private final SpecialistProfileService service;

    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approveSpecialist(@PathVariable("id") @NotNull(message = "Id is required.")
                                               @ValidUuid(paramName = "id") String id) {
        service.approve(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
