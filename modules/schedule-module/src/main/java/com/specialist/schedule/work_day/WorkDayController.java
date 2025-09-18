package com.specialist.schedule.work_day;

import com.specialist.contracts.auth.PrincipalDetails;
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
@RequiredArgsConstructor
public class WorkDayController {

    private final WorkDayService service;
    

    @GetMapping("/{specialist_id}")
    public ResponseEntity<?> getAvailableTimes(@PathVariable("specialist_id") UUID specialistId,
                                               @RequestParam("date")
                                               @DateTimeFormat(pattern = "yyyy-MM-dd")
                                               @NotNull(message = "Date shouldn't be null!")
                                               LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAvailableDayTimes(specialistId, date));
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @GetMapping("/me")
    public ResponseEntity<?> getAllTimes(@AuthenticationPrincipal PrincipalDetails principal,
                                         @RequestParam("date")
                                         @DateTimeFormat(pattern = "yyyy-MM-dd")
                                         @NotNull(message = "Date shouldn't be null!")
                                         LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllDayTimes(principal.getAccountId(), date));
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestParam("date")
                                    @DateTimeFormat(pattern = "yyyy-MM-dd")
                                    @NotNull(message = "Date shouldn't be null!")
                                    LocalDate date) {
        service.deleteAllBySpecialistIdAndDate(principal.getAccountId(), date);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}