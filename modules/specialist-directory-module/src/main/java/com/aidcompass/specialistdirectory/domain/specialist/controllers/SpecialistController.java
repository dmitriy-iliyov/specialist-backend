package com.aidcompass.specialistdirectory.domain.specialist.controllers;

import com.aidcompass.specialistdirectory.domain.contact.ContactType;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.services.SpecialistCountServiceImpl;
import com.aidcompass.specialistdirectory.domain.specialist.services.SpecialistService;
import com.aidcompass.specialistdirectory.utils.PageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/specialists")
@RequiredArgsConstructor
public class SpecialistController {

    private final SpecialistService service;
    private final SpecialistCountServiceImpl countService;


    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid PageRequest page){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByRatingDesc(page));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getAllByFilter(@ModelAttribute @Valid SpecialistFilter filter){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByFilter(filter));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countService.countAll());
    }

    @GetMapping("/count/filter")
    public ResponseEntity<?> countByFilter(@ModelAttribute @Valid SpecialistFilter filter){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countService.countByFilter(filter));
    }

    @GetMapping("/info/contact-types")
    public ResponseEntity<?> getContactType() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ContactType.values());
    }
}
