package com.specialist.specialistdirectory.domain.type.controllers;

import com.specialist.specialistdirectory.domain.type.services.TypeReadService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/types")
@RequiredArgsConstructor
public class TypeController {

    private final TypeReadService orchestrator;

    @GetMapping("/json")
    public ResponseEntity<?> getAllAsJson(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findAllApprovedAsJson(request));
    }

    @GetMapping("/map")
    public ResponseEntity<?> getAllAsMap(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.findAllApprovedAsMap(request));
    }
}