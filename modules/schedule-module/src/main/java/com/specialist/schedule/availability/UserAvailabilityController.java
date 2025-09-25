package com.specialist.schedule.availability;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specialists/{specialist_id}/availability")
@RequiredArgsConstructor
public class UserAvailabilityController {

    private final UserAvailabilityAggregator aggregator;

    @GetMapping
    public ResponseEntity<?> getDay(@PathVariable("specialist_id") UUID specialistId,
                                    @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")
                                    @NotNull(message = "Date shouldn't be null!")
                                    LocalDate date) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(aggregator.aggregateDay(specialistId, date));
    }

    @GetMapping("/month")
    public ResponseEntity<?> getMonth(@PathVariable("specialist_id") UUID specialistId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(aggregator.aggregateMonth(specialistId));
    }
}