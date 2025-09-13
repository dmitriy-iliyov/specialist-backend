package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.contracts.user.UserType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistManagementOrchestrator;
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
@PreAuthorize("hasAnyRole('USER', 'SPECIALIST')")
@RequiredArgsConstructor
public class SpecialistManagementController {

    private final SpecialistManagementOrchestrator orchestrator;

    @PreAuthorize("hasAuthority('SPECIALIST_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id,
                                    @RequestBody @Valid SpecialistUpdateDto dto) {
        dto.setAccountId(principal.getAccountId());
        dto.setId(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(dto, UserType.fromStringRole(principal.getStringRole())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        orchestrator.delete(principal.getAccountId(), UUID.fromString(id), UserType.fromStringRole(principal.getStringRole()));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
