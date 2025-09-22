package com.specialist.specialistdirectory.domain.review.controllers;

import com.specialist.specialistdirectory.domain.review.models.enums.DeliveryState;
import com.specialist.specialistdirectory.domain.review.services.CreatorRatingBufferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/system/v1/creator-rating-buffer/batch")
@PreAuthorize("hasRole('SERVICE') && hasAuthority('REVIEWS_BUFFER_MANAGEMENT')")
@RequiredArgsConstructor
public class CreatorRatingBufferController {

    private final CreatorRatingBufferService service;

    @PostMapping
    public ResponseEntity<?> markBatchAsReadyToSend(@RequestBody Map<String, Set<UUID>> dto) {
        service.updateAllDeliveryStateByIdIn(dto.get("ids"), DeliveryState.READY_TO_SEND);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteBatchByIdIn(@RequestBody Map<String, Set<UUID>> dto) {
        service.deleteAllByIdIn(dto.get("ids"));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
