package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistPersistOrchestrator;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.utils.validation.annotation.ValidUuid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/specialists")
@PreAuthorize("hasRole('ADMIN') && hasAuthority('SPECIALIST_MANAGEMENT')")
@RequiredArgsConstructor
public class AdminSpecialistController {

    private final SpecialistService service;
    private final SpecialistPersistOrchestrator persistOrchestrator;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid SpecialistCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(persistOrchestrator.save(principal.getAccountId(), CreatorType.ADMIN, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") @ValidUuid(paramName = "id") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(UUID.fromString(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> approve(@AuthenticationPrincipal PrincipalDetails principal,
                                     @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        service.approve(UUID.fromString(id), principal.getAccountId(), ApproverType.ADMIN);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @ValidUuid(paramName = "id") String id,
                                    @RequestBody @Valid SpecialistUpdateDto dto) {
        dto.setId(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @ValidUuid(paramName = "id") String id) {
        service.deleteById(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}