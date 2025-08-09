package com.specialist.specialistdirectory.domain.type.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.type.models.dtos.FullTypeCreateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.FullTypeUpdateDto;
import com.specialist.specialistdirectory.domain.type.services.ApproveTypeOrchestrator;
import com.specialist.specialistdirectory.domain.type.services.TypeOrchestrator;
import com.specialist.utils.pagination.PageRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/v1/types")
@PreAuthorize("hasRole('ADMIN') && hasAuthority('TYPE_MANAGEMENT')")
@RequiredArgsConstructor
public class AdminTypeController {

    private final TypeOrchestrator orchestrator;
    private final ApproveTypeOrchestrator approveOrchestrator;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid FullTypeCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(principal.getUserId(), dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @NotNull(message = "Id is required.")
                                    @Positive(message = "Id should be positive.") Long id,
                                    @RequestBody FullTypeUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(id, dto));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable("id") @NotNull(message = "Id is required.")
                                     @Positive(message = "Id should be positive.") Long id) {
        approveOrchestrator.approve(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @NotNull(message = "Id is required.")
                                    @Positive(message = "Id should be positive.") Long id) {
        orchestrator.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findAll(page));
    }

    @GetMapping("/unapproved")
    public ResponseEntity<?> getAllUnapproved(@ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findAllUnapproved(page));
    }
}