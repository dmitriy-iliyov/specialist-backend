package com.specialist.user.controllers;

import com.specialist.user.models.enums.ScopeType;
import com.specialist.user.services.SpecialistService;
import com.specialist.user.services.UserService;
import com.specialist.utils.pagination.PageRequest;
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
public class AdminUserController {

    private final UserService service;
    private final SpecialistService specialistService;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @NotNull(message = "Id is required.")
                                     @ValidUuid(paramName = "id") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateById(UUID.fromString(id)));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAll(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll(ScopeType.PRIVATE, page));
    }

    @PatchMapping("/specialists/approve/{id}")
    public ResponseEntity<?> approveSpecialist(@PathVariable("id") @NotNull(message = "Id is required.")
                                               @ValidUuid(paramName = "id") String id) {
        specialistService.approve(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
