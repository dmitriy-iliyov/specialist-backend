package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.admin.AdminSpecialistService;
import com.specialist.utils.uuid.UUIDv7;
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

    private final AdminSpecialistService facade;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid SpecialistCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(facade.save(principal.getAccountId(), dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id")
                                 @UUIDv7(paramName = "id", message = "Id should have valid format.") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.findById(UUID.fromString(id)));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid AdminSpecialistFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.findAll(filter));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approve(@AuthenticationPrincipal PrincipalDetails principal,
                                     @PathVariable("id")
                                     @UUIDv7(paramName = "id", message = "Id should have valid format.") String id) {
        facade.approve(UUID.fromString(id), principal.getAccountId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id")
                                    @UUIDv7(paramName = "id", message = "Id should have valid format.") String id,
                                    @RequestBody @Valid SpecialistUpdateDto dto) {
        dto.setId(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")
                                    @UUIDv7(paramName = "id", message = "Id should have valid format.") String id) {
        facade.deleteById(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}