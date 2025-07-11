package com.aidcompass.schedule.interval;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.schedule.interval.models.dto.IntervalCreateDto;
import com.aidcompass.schedule.interval.models.dto.IntervalResponseDto;
import com.aidcompass.schedule.interval.services.IntervalOrchestrator;
import com.aidcompass.schedule.interval.services.NearestIntervalService;
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
@PreAuthorize("hasAnyAuthority('ROLE_DOCTOR', 'ROLE_JURIST')")
@RequiredArgsConstructor
public class IntervalController {

    private final IntervalOrchestrator orchestrator;
    private final NearestIntervalService nearestService;


    @PostMapping("/me")
    public ResponseEntity<?> createInterval(@AuthenticationPrincipal PrincipalDetails principal,
                                            @RequestBody @Valid IntervalCreateDto dto,
                                            @RequestParam(value = "return_body", defaultValue = "false")
                                            boolean returnBody) {
        IntervalResponseDto response = orchestrator.save(principal.getUserId(), dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/nearest/{owner_id}")
    public ResponseEntity<?> getNearestInterval(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(nearestService.findByOwnerId(ownerId));
    }

    @DeleteMapping("/me/{id}")
    public ResponseEntity<?> deleteInterval(@AuthenticationPrincipal PrincipalDetails principal,
                                            @PathVariable("id")
                                            @NotNull(message = "Id shouldn't be null!")
                                            @Positive(message = "Id should be positive!")
                                            Long id) {
        orchestrator.delete(principal.getUserId(), id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}