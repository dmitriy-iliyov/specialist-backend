package com.aidcompass.specialistdirectory.domain.specialist_type.controllers;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.FullTypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.FullTypeUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeApproveOrchestrator;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeOrchestrator;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeService;
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
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminTypeController {

    private final TypeOrchestrator orchestrator;
    private final TypeApproveOrchestrator approveOrchestrator;


    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid FullTypeCreateDto dto) {
        dto.type().setCreatorId(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id")
                                    @NotNull(message = "Id is required.")
                                    @Positive(message = "Id should be positive.") Long id,
                                    @RequestBody @Valid FullTypeUpdateDto dto) {
        dto.type().setId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(dto));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable("id")
                                     @NotNull(message = "Id is required.")
                                     @Positive(message = "Id should be positive.") Long id) {
        approveOrchestrator.approve(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")
                                    @NotNull(message = "Id is required.")
                                    @Positive(message = "Id should be positive.") Long id) {
        orchestrator.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findAll());
    }

    @GetMapping("/unapproved")
    public ResponseEntity<?> getAllUnapproved() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findAllUnapproved());
    }
}