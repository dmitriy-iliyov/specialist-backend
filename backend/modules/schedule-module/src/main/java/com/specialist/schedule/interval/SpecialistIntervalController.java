package com.specialist.schedule.interval;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.schedule.interval.models.dto.IntervalCreateDto;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.services.DayDeleteService;
import com.specialist.schedule.interval.services.IntervalOrchestrator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/specialists/me/intervals")
@PreAuthorize("hasRole('SPECIALIST')")
@RequiredArgsConstructor
public class SpecialistIntervalController {

    private final IntervalOrchestrator orchestrator;
    private final DayDeleteService deleteService;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @NotNull(message = "Id shouldn't be null!")
                                    @Positive(message = "Id should be positive!") Long id) {
        orchestrator.delete(principal.getAccountId(), id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllByDate(@AuthenticationPrincipal PrincipalDetails principal,
                                             @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")
                                             @NotNull(message = "Date shouldn't be null!")
                                             LocalDate date) {
        deleteService.deleteAllBySpecialistIdAndDate(principal.getAccountId(), date);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}