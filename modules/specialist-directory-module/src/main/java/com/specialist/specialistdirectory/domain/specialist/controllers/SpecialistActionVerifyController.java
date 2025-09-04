package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.specialistdirectory.domain.specialist.services.SpecialistActionOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/specialists")
@RequiredArgsConstructor
public class SpecialistActionVerifyController {

    private final SpecialistActionOrchestrator orchestrator;

    @PostMapping("/recall/{code}")
    public ResponseEntity<?> recall(@PathVariable("code") String code) {
        orchestrator.recall(code);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @PostMapping("/manage/{code}")
    public ResponseEntity<?> manage(@PathVariable("code") String code) {
        orchestrator.manage(code);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
