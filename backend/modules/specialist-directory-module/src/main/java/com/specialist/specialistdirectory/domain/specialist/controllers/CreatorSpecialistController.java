package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.creator.CreatorSpecialistService;
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

    private final CreatorSpecialistService facade;

    // DISCUSS: to del?
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal,
                                 @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.findById(principal.getAccountId(), UUID.fromString(id)));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal PrincipalDetails principal,
                                    @ModelAttribute @Valid ExtendedSpecialistFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.findAllByFilter(principal.getAccountId(), filter));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(@AuthenticationPrincipal PrincipalDetails principal){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.count(principal.getAccountId()));
    }
}