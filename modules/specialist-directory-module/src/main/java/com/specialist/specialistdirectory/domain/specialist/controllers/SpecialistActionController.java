package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ContactType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistActionOrchestrator;
import com.specialist.utils.validation.annotation.ValidUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specialists/{id}/action")
@RequiredArgsConstructor
public class SpecialistActionController {

    private final SpecialistActionOrchestrator orchestrator;

    @PostMapping("/recall/{contact_type}")
    public ResponseEntity<?> recall(@PathVariable("id") @ValidUuid(paramName = "id") String id,
                                    @PathVariable("contact_type") ContactType contactType) {
        orchestrator.recallRequest(UUID.fromString(id), contactType);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @PostMapping("/manage/{contact_type}")
    public ResponseEntity<?> manage(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id,
                                    @PathVariable("contact_type")ContactType contactType) {
        orchestrator.manageRequest(UUID.fromString(id), principal.getAccountId(), contactType);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
