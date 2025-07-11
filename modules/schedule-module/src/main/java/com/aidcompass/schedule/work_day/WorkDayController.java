package com.aidcompass.schedule.work_day;

import com.aidcompass.contracts.PrincipalDetails;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/days")
@PreAuthorize("hasAnyAuthority('ROLE_DOCTOR', 'ROLE_JURIST')")
@RequiredArgsConstructor
public class WorkDayController {

    private final WorkDayService service;
    

    @PreAuthorize("permitAll()")
    @GetMapping("/{owner_id}")
    public ResponseEntity<?> getAvailableTimes(@PathVariable("owner_id") UUID ownerId,
                                               @RequestParam("date")
                                               @DateTimeFormat(pattern = "yyyy-MM-dd")
                                               @NotNull(message = "Date shouldn't be null!")
                                               LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAvailableDayTimes(ownerId, date));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAllTimes(@AuthenticationPrincipal PrincipalDetails principal,
                                         @RequestParam("date")
                                         @DateTimeFormat(pattern = "yyyy-MM-dd")
                                         @NotNull(message = "Date shouldn't be null!")
                                         LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllDayTimes(principal.getUserId(), date));
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestParam("date")
                                    @DateTimeFormat(pattern = "yyyy-MM-dd")
                                    @NotNull(message = "Date shouldn't be null!")
                                    LocalDate date) {
        service.deleteAllByVolunteerIdAndDate(principal.getUserId(), date);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}