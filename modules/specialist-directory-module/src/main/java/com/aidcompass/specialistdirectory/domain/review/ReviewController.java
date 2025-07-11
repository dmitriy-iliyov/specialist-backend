package com.aidcompass.specialistdirectory.domain.review;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewCreateDto;
import com.aidcompass.specialistdirectory.domain.review.models.dtos.ReviewUpdateDto;
import com.aidcompass.specialistdirectory.domain.review.services.ReviewFacade;
import com.aidcompass.specialistdirectory.domain.review.services.ReviewService;
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

    private final ReviewService service;
    private final ReviewFacade facade;


    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id") @ValidUuid UUID specialistId,
                                    @RequestBody @Valid ReviewCreateDto dto) {
        dto.setCreatorId(principal.getUserId());
        dto.setSpecialistId(specialistId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(dto));
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id") @ValidUuid UUID specialistId,
                                    @RequestBody @Valid ReviewUpdateDto dto) {
        dto.setCreatorId(principal.getUserId());
        dto.setSpecialistId(specialistId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(dto));
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialist_id") @ValidUuid UUID specialistId,
                                    @PathVariable("id") @ValidUuid UUID id) {
        service.delete(principal.getUserId(), specialistId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}