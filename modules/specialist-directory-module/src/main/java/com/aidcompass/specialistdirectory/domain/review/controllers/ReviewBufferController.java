package com.aidcompass.specialistdirectory.domain.review.controllers;

import com.aidcompass.specialistdirectory.domain.review.models.dtos.DeleteRequestDto;
import com.aidcompass.specialistdirectory.domain.review.services.ReviewBufferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system/v1/specialists/reviews/buffer/batch")
@RequiredArgsConstructor
public class ReviewBufferController {

    private final ReviewBufferService service;

    @DeleteMapping
    public ResponseEntity<?> deleteAllByIdIn(@RequestBody DeleteRequestDto dto) {
        service.popAllByIdIn(dto.ids());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
