package com.specialist.profile.controllers;

import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.profile.services.ProfileQueryService;
import com.specialist.profile.services.ProfileReadOrchestrator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileReadOrchestrator orchestrator;
    private final ProfileQueryService queryService;

    public ProfileController(ProfileReadOrchestrator orchestrator,
                             @Qualifier("userProfileQueryService") ProfileQueryService queryService) {
        this.orchestrator = orchestrator;
        this.queryService = queryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @NotNull(message = "Id is required.") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findPublicById(UUID.fromString(id)));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid ProfileFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryService.findAll(filter));
    }
}
