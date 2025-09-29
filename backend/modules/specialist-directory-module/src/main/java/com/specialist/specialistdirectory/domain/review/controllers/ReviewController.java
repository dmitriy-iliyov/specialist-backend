package com.specialist.specialistdirectory.domain.review.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.specialistdirectory.domain.review.services.ReviewAggregator;
import com.specialist.specialistdirectory.domain.review.services.ReviewManagementOrchestrator;
import com.specialist.utils.validation.annotation.ValidUuid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specialists/{specialist_id}/reviews")
public class ReviewController {

    private final ReviewManagementOrchestrator orchestrator;
    private final ReviewAggregator aggregator;

    public ReviewController(@Qualifier("reviewManagementRetryDecorator") ReviewManagementOrchestrator orchestrator,
                            ReviewAggregator aggregator) {
        this.orchestrator = orchestrator;
        this.aggregator = aggregator;
    }

    @PreAuthorize("hasAnyRole('USER', 'SPECIALIST') && hasAuthority('REVIEW_CREATE_UPDATE')")
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id")
                                    @ValidUuid(paramName = "specialist_id") String specialistId,
                                    @RequestBody @Valid ReviewCreateDto dto) {
        dto.setCreatorId(principal.getAccountId());
        dto.setSpecialistId(UUID.fromString(specialistId));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }

    @PreAuthorize("hasAnyRole('USER', 'SPECIALIST') && hasAuthority('REVIEW_CREATE_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id")
                                    String id,
                                    @PathVariable("specialist_id") @ValidUuid(paramName = "specialist_id")
                                    String specialistId,
                                    @RequestBody @Valid ReviewUpdateDto dto) {
        dto.setId(UUID.fromString(id));
        dto.setCreatorId(principal.getAccountId());
        dto.setSpecialistId(UUID.fromString(specialistId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(dto));
    }

    @PreAuthorize("hasAnyRole('USER', 'SPECIALIST')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id") @ValidUuid(paramName = "specialist_id")
                                    String specialistId,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        orchestrator.delete(principal.getAccountId(), UUID.fromString(specialistId), UUID.fromString(id));
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