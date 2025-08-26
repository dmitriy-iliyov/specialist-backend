package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistCountService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistCreatorOrchestrator;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistPersistOrchestrator;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.utils.pagination.PageRequest;
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
@RequestMapping("/api/v1/me/specialists")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class CreatorSpecialistController {

    private final SpecialistPersistOrchestrator persistOrchestrator;
    private final SpecialistService service;
    private final SpecialistCreatorOrchestrator orchestrator;
    private final SpecialistCountService countService;

    @PreAuthorize("hasAuthority('SPECIALIST_CREATE_UPDATE')")
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid SpecialistCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(persistOrchestrator.save(principal.getAccountId(), CreatorType.USER, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal,
                                 @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByCreatorIdAndId(principal.getAccountId(), UUID.fromString(id)));
    }

    @PreAuthorize("hasAuthority('SPECIALIST_CREATE_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id,
                                    @RequestBody @Valid SpecialistUpdateDto dto) {
        dto.setCreatorId(principal.getAccountId());
        dto.setId(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        orchestrator.delete(principal.getAccountId(), UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAllCreated(@AuthenticationPrincipal PrincipalDetails principal,
                                           @ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByCreatorId(principal.getAccountId(), page));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getAllCreatedByFilter(@AuthenticationPrincipal PrincipalDetails principal,
                                                   @ModelAttribute @Valid ExtendedSpecialistFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByCreatorIdAndFilter(principal.getAccountId(), filter));
    }

    @GetMapping("/count")
    public ResponseEntity<?> countByCreatorId(@AuthenticationPrincipal PrincipalDetails principal){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countService.countByCreatorId(principal.getAccountId()));
    }
}