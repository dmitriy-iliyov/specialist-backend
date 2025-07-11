package com.aidcompass.specialistdirectory.domain.specialist_type.controllers;

import com.aidcompass.specialistdirectory.domain.specialist_type.services.TypeService;
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

    private final TypeService service;


    @GetMapping("/json")
    public ResponseEntity<?> getAllAsJson() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllApprovedAsJson());
    }

    @GetMapping("/map")
    public ResponseEntity<?> getAllAsMap() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllApprovedAsMap());
    }
}
