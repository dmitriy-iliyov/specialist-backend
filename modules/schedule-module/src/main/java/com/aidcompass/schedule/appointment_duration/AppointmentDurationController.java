package com.aidcompass.schedule.appointment_duration;


import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.core.security.domain.authority.models.Authority;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments/duration")
@RequiredArgsConstructor
public class AppointmentDurationController {

    private final AppointmentDurationService service;


    @PreAuthorize("hasAnyAuthority('ROLE_DOCTOR', 'ROLE_JURIST')")
    @PutMapping("/me")
    public ResponseEntity<?> set(@AuthenticationPrincipal PrincipalDetails principal,
                                 @RequestParam("duration")
                                 @NotNull(message = "Duration shouldn't be null!")
                                 @Min(value = 15, message = "Duration should be at least 15 minutes.")
                                 @Max(value = 60, message = "Duration should be at most 60 minutes.")
                                 Long duration,
                                 @RequestParam(value = "return_body", defaultValue = "false")
                                 boolean returnBody) {
        List<Authority> authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(Authority::valueOf)
                .toList();
        Authority authority;
        if (authorities.contains(Authority.ROLE_JURIST)) {
            authority = Authority.ROLE_JURIST;
        } else {
            authority = Authority.ROLE_DOCTOR;
        }
        Long response = service.set(principal.getUserId(), authority, duration);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_DOCTOR', 'ROLE_JURIST')")
    @GetMapping("/me")
    public ResponseEntity<?> getPrivate(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByOwnerId(principal.getUserId()));
    }

    @GetMapping("/{owner_id}")
    public ResponseEntity<?> get(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByOwnerId(ownerId));
    }
}
