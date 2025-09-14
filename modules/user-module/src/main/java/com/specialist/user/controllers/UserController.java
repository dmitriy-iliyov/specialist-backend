package com.specialist.user.controllers;

import com.specialist.user.models.enums.ScopeType;
import com.specialist.user.services.ProfileReadOrchestrator;
import com.specialist.user.services.UserService;
import com.specialist.utils.pagination.PageRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class UserController {

    private final ProfileReadOrchestrator orchestrator;
    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @NotNull(message = "Id is required.") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findPublicById(UUID.fromString(id)));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAll(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll(ScopeType.PUBLIC, page));
    }
}
