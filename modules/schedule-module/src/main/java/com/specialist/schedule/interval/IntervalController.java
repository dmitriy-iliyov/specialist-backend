package com.specialist.schedule.interval;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.schedule.interval.models.dto.IntervalCreateDto;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.services.IntervalOrchestrator;
import com.specialist.schedule.interval.services.NearestIntervalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/intervals")
@RequiredArgsConstructor
public class IntervalController {

    private final IntervalOrchestrator orchestrator;
    private final NearestIntervalService nearestService;

    @PreAuthorize("hasRole('SPECIALIST')")
    @PostMapping("/me")
    public ResponseEntity<?> createInterval(@AuthenticationPrincipal PrincipalDetails principal,
                                            @RequestBody @Valid IntervalCreateDto dto,
                                            @RequestParam(value = "return_body", defaultValue = "false")
                                            boolean returnBody) {
        IntervalResponseDto response = orchestrator.save(principal.getAccountId(), dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/nearest/{specialist_id}")
    public ResponseEntity<?> getNearestInterval(@PathVariable("specialist_id") UUID specialistId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(nearestService.findBySpecialistId(specialistId));
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @DeleteMapping("/me/{id}")
    public ResponseEntity<?> deleteInterval(@AuthenticationPrincipal PrincipalDetails principal,
                                            @PathVariable("id") @NotNull(message = "Id shouldn't be null!")
                                            @Positive(message = "Id should be positive!") Long id) {
        orchestrator.delete(principal.getAccountId(), id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}