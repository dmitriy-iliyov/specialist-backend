package com.aidcompass.specialistdirectory.domain.review.controllers;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.aidcompass.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.aidcompass.specialistdirectory.domain.review.services.interfases.ReviewAggregator;
import com.aidcompass.specialistdirectory.domain.review.services.interfases.ReviewOrchestrator;
import com.aidcompass.specialistdirectory.utils.validation.ValidUuid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specialists/{specialist_id}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewOrchestrator orchestrator;
    private final ReviewAggregator aggregator;


    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id")
                                    @ValidUuid(paramName = "specialist_id") String specialistId,
                                    @RequestBody @Valid ReviewCreateDto dto) {
        dto.setCreatorId(principal.getUserId());
        dto.setSpecialistId(UUID.fromString(specialistId));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id") @ValidUuid(paramName = "specialist_id")
                                    String specialistId,
                                    @RequestBody @Valid ReviewUpdateDto dto) {
        dto.setCreatorId(principal.getUserId());
        dto.setSpecialistId(UUID.fromString(specialistId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(dto));
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id") @ValidUuid(paramName = "specialist_id")
                                    String specialistId,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        orchestrator.delete(principal.getUserId(), UUID.fromString(specialistId), UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable("specialist_id") @ValidUuid(paramName = "specialist_id")
                                    String specialistId,
                                    @ModelAttribute @Valid ReviewSort sort) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(aggregator.findAllWithSortBySpecialistId(UUID.fromString(specialistId), sort));
    }
}