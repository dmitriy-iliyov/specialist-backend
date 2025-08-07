package com.specialist.specialistdirectory.domain.review.controllers;

import com.specialist.specialistdirectory.domain.review.services.ReviewBufferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/system/v1/specialists/reviews/buffer/batch")
@PreAuthorize("hasRole('SERVICE') && hasAuthority('REVIEWS_BUFFER_MANAGEMENT')")
@RequiredArgsConstructor
public class ReviewBufferController {

    private final ReviewBufferService service;

    @PostMapping
    public ResponseEntity<?> markBatchAsReadyToSend(@RequestBody Map<String, Set<UUID>> dto) {
        service.markBatchAsReadyToSend(dto.get("ids"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBatchByIdIn(@RequestBody Map<String, Set<UUID>> dto) {
        service.popAllByIdIn(dto.get("ids"));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
