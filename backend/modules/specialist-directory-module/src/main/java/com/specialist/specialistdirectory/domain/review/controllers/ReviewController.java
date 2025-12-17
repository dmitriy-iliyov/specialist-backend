package com.specialist.specialistdirectory.domain.review.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewDeleteRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateRequest;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.specialistdirectory.domain.review.services.ReviewAggregator;
import com.specialist.specialistdirectory.domain.review.services.ReviewManagementFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specialists/{specialist_id}/reviews")
public class ReviewController {

    private final ReviewManagementFacade facade;
    private final ReviewAggregator aggregator;

    public ReviewController(@Qualifier("reviewManagementRetryDecorator") ReviewManagementFacade facade,
                            ReviewAggregator aggregator) {
        this.facade = facade;
        this.aggregator = aggregator;
    }

    @PreAuthorize("hasAnyRole('USER', 'SPECIALIST') && hasAuthority('REVIEW_CREATE_UPDATE')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id") UUID specialistId,
                                    @RequestParam("review") String rawPayload,
                                    @RequestParam("picture") MultipartFile picture) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(facade.save(ReviewCreateRequest.of(principal.getAccountId(), specialistId, rawPayload, picture)));
    }

    @PreAuthorize("hasAnyRole('USER', 'SPECIALIST') && hasAuthority('REVIEW_CREATE_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") UUID id,
                                    @PathVariable("specialist_id") UUID specialistId,
                                    @RequestParam("review") String rawPayload,
                                    @RequestParam("picture") MultipartFile picture) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.update(ReviewUpdateRequest.of(id, principal.getAccountId(), specialistId, rawPayload, picture)));
    }

    @PreAuthorize("hasAnyRole('USER', 'SPECIALIST')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id") UUID specialistId,
                                    @PathVariable("id") UUID id) {
        facade.delete(ReviewDeleteRequest.of(id, specialistId, principal.getAccountId()));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable("specialist_id") UUID specialistId,
                                    @ModelAttribute @Valid ReviewSort sort) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(aggregator.findAllWithSortBySpecialistId(specialistId, sort));
    }
}