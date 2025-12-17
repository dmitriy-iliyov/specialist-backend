package com.specialist.profile.controllers;

import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.profile.services.ProfileReadService;
import com.specialist.utils.uuid.UUIDv7;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileReadService orchestrator;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id")
                                     @UUIDv7(paramName = "id", message = "Id should have valid format.") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findPublicById(UUID.fromString(id)));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid ProfileFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findAll(ScopeType.PUBLIC, filter));
    }
}
