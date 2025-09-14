package com.specialist.specialistdirectory.domain.review.controllers;

import com.specialist.specialistdirectory.domain.review.services.AdminReviewOrchestrator;
import com.specialist.utils.validation.annotation.ValidUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/specialists/{specialist_id}/reviews")
@PreAuthorize("hasRole('ADMIN') && hasAuthority('REVIEW_MANAGEMENT')")
@RequiredArgsConstructor
public class AdminReviewController {

    private final AdminReviewOrchestrator orchestrator;

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("specialist_id") @ValidUuid(paramName = "specialist_id")
                                    String specialistId,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        orchestrator.delete(UUID.fromString(specialistId), UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
