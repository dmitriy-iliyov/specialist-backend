package com.specialist.specialistdirectory.domain.review.controllers;

import com.specialist.specialistdirectory.domain.review.models.filters.AdminReviewSort;
import com.specialist.specialistdirectory.domain.review.services.AdminReviewManagementFacade;
import com.specialist.utils.uuid.annotation.ValidUuid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/specialists/{specialist_id}/reviews")
@PreAuthorize("hasRole('ADMIN') && hasAuthority('REVIEW_MANAGEMENT')")
public class AdminReviewController {

    private final AdminReviewManagementFacade facade;

    public AdminReviewController(@Qualifier("adminReviewManagementRetryDecorator") AdminReviewManagementFacade facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PathVariable("specialist_id") String specialistId,
                                    @ModelAttribute @Valid AdminReviewSort sort) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.getAll(UUID.fromString(specialistId), sort));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable("specialist_id") String specialistId,
                                     @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        facade.approve(UUID.fromString(specialistId), UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("specialist_id") String specialistId,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        facade.delete(UUID.fromString(specialistId), UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
