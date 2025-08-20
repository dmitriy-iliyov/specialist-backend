package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistLanguage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/specialists/languages")
public class SpecialistLanguageController {

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SpecialistLanguage.values());
    }
}
