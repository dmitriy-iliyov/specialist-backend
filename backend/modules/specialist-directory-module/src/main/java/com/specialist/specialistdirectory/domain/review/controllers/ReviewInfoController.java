package com.specialist.specialistdirectory.domain.review.controllers;

import com.specialist.specialistdirectory.domain.review.models.enums.ReviewStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/info")
public class ReviewInfoController {

    @PreAuthorize("hasAnyRole('ADMIN', 'SERVICE')")
    @GetMapping("/review-statuses")
    public ResponseEntity<?> getReviewStatus() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReviewStatus.values());
    }
}
