package com.specialist.schedule.availability;

import com.specialist.contracts.auth.PrincipalDetails;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/specialists/me/availability")
@PreAuthorize("hasRole('SPECIALIST')")
@RequiredArgsConstructor
public class SpecialistAvailabilityController {

    private final SpecialistAvailabilityAggregator aggregator;

    @GetMapping
    public ResponseEntity<?> getDay(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")
                                    @NotNull(message = "Date shouldn't be null!")
                                    LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(aggregator.findDay(principal.getAccountId(), date));
    }

    @GetMapping("/month")
    public ResponseEntity<?> getMonth(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(aggregator.findMonth(principal.getAccountId()));
    }
}
