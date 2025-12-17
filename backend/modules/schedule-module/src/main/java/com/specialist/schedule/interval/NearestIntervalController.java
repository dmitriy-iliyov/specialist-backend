package com.specialist.schedule.interval;

import com.specialist.schedule.interval.services.NearestIntervalService;
import com.specialist.utils.uuid.UUIDv7;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specialists/{specialist_id}/intervals/nearest")
@RequiredArgsConstructor
public class NearestIntervalController {

    private final NearestIntervalService service;

    @GetMapping
    public ResponseEntity<?> getNearestInterval(@PathVariable("specialist_id")
                                                @UUIDv7(paramName = "specialist_id", message = "Id should have valid format.")
                                                String specialistId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findBySpecialistId(UUID.fromString(specialistId)));
    }
}
