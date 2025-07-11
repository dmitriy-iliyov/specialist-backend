package com.aidcompass.schedule;

import com.aidcompass.contracts.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/timetable")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService service;


    @PreAuthorize("permitAll()")
    @GetMapping("/{owner_id}/month/dates")
    public ResponseEntity<?> getAvailableMonthDates(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAvailableMonthDates(ownerId));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_DOCTOR', 'ROLE_JURIST')")
    @GetMapping("/me/month/dates")
    public ResponseEntity<?> getAllMonthDates(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllMonthDates(principal.getUserId()));
    }
}
