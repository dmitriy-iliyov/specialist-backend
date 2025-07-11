package com.aidcompass.aggregator.api.appointment;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.schedule.appointment.models.dto.StatusFilter;
import com.aidcompass.core.security.domain.authority.models.Authority;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aggregator/appointments")
@RequiredArgsConstructor
public class AppointmentAggregatorController {

    private final AppointmentAggregatorService service;


    @PreAuthorize("hasAnyAuthority('ROLE_JURIST', 'ROLE_DOCTOR')")
    @GetMapping("/me/{id}")
    public ResponseEntity<?> getVolunteerAppointment(@AuthenticationPrincipal PrincipalDetails principal,
                                                     @PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findFullAppointment(principal.getUserId(), id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER', 'ROLE_JURIST', 'ROLE_DOCTOR')")
    @GetMapping("/filter")
    public ResponseEntity<?> getAppointments(@AuthenticationPrincipal PrincipalDetails principal,
                                             @RequestParam(value = "scheduled", defaultValue = "false")
                                             boolean scheduled,
                                             @RequestParam(value = "completed", defaultValue = "false")
                                             boolean completed,
                                             @RequestParam(value = "canceled", defaultValue = "false")
                                             boolean canceled,
                                             @RequestParam(value = "page", defaultValue = "0")
                                             @PositiveOrZero(message = "Page should be positive!")
                                             int page,
                                             @RequestParam(value = "size", defaultValue = "10")
                                             @Min(value = 10, message = "Size must be at least 10!")
                                             int size) {
        List<Authority> authorityList = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(Authority::valueOf)
                .toList();
        StatusFilter filter = new StatusFilter(scheduled, canceled, completed);
        if (authorityList.contains(Authority.ROLE_CUSTOMER)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(service.findByFilterAndCustomerId(principal.getUserId(), filter, page, size));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByFilterAndVolunteerId(principal.getUserId(), filter, page, size));
    }
}
