package com.aidcompass.specialistdirectory.domain.review.controllers;

import com.aidcompass.specialistdirectory.domain.review.services.ReviewOrchestrator;
import com.aidcompass.specialistdirectory.utils.validation.ValidUuid;
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
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class ReviewAdminController {

    private final ReviewOrchestrator orchestrator;


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("specialist_id") @ValidUuid(paramName = "specialist_id")
                                    String specialistId,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        orchestrator.adminDelete(UUID.fromString(specialistId), UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
