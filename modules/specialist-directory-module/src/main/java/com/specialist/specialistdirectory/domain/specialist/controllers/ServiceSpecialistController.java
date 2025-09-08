package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistPersistOrchestrator;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistStatusService;
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
@RequestMapping("/api/system/v1/specialists")
@PreAuthorize("hasRole('SERVICE') && hasAuthority('SPECIALIST_CREATE')")
@RequiredArgsConstructor
public class ServiceSpecialistController {

    private final SpecialistPersistOrchestrator persistOrchestrator;
    private final SpecialistStatusService statusService;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid SpecialistCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(persistOrchestrator.save(principal.getAccountId(), CreatorType.SERVICE, dto));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approve(@AuthenticationPrincipal PrincipalDetails principal,
                                     @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        statusService.approve(UUID.fromString(id), principal.getAccountId(), ApproverType.SERVICE);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
