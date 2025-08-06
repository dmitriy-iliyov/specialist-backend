package com.aidcompass.user.controllers;

import com.aidcompass.user.models.enums.ScopeType;
import com.aidcompass.user.services.UserService;
import com.aidcompass.utils.pagination.PageRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @NotNull(message = "Id is required.") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateById(UUID.fromString(id)));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll(ScopeType.PRIVATE, page));
    }
}
