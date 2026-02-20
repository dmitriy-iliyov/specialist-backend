package com.specialist.schedule.appointment_duration;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.utils.uuid.UUIDv7;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments/duration")
@RequiredArgsConstructor
public class AppointmentDurationController {

    private final AppointmentDurationService service;

    @PreAuthorize("hasRole('SPECIALIST')")
    @PutMapping("/me")
    public ResponseEntity<?> set(@AuthenticationPrincipal PrincipalDetails principal,
                                 @RequestParam("duration") @NotNull(message = "Duration shouldn't be null!")
                                 @Min(value = 15, message = "Duration should be at least 15 minutes.")
                                 @Max(value = 60, message = "Duration should be at most 60 minutes.")
                                 Long duration,
                                 @RequestParam(value = "return_body", defaultValue = "false")
                                 boolean returnBody) {
        Long response = service.set(principal.getAccountId(), duration);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @GetMapping("/me")
    public ResponseEntity<?> getPrivate(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findBySpecialistId(principal.getAccountId()));
    }

    @GetMapping("/{specialist_id}")
    public ResponseEntity<?> get(@PathVariable("specialist_id")
                                 @UUIDv7(paramName = "specialist_id", message = "Id should have valid format.") String specialistId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findBySpecialistId(UUID.fromString(specialistId)));
    }
}
