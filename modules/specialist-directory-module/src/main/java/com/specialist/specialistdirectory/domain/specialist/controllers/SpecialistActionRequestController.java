package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.contracts.specialistdirectory.dto.ContactType;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistActionFacade;
import com.specialist.utils.uuid.UUIDv7;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specialists/{id}")
@RequiredArgsConstructor
public class SpecialistActionRequestController {

    private final SpecialistActionFacade facade;

    @PostMapping("/recall")
    public ResponseEntity<?> recallRequest(@PathVariable("id")
                                           @UUIDv7(paramName = "id", message = "Id should have valid format.") String id,
                                           @RequestParam("contact_type") ContactType contactType) {
        facade.recallRequest(UUID.fromString(id), contactType);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @PostMapping("/manage")
    public ResponseEntity<?> manageRequest(@AuthenticationPrincipal PrincipalDetails principal,
                                           @PathVariable("id")
                                           @UUIDv7(paramName = "id", message = "Id should have valid format.") String id,
                                           @RequestParam("contact_type") ContactType contactType) {
        facade.manageRequest(UUID.fromString(id), principal.getAccountId(), contactType);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}