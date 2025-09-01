package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistAggregator;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistCountService;
import com.specialist.utils.pagination.PageRequest;
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

    private final SpecialistAggregator aggregator;
    private final SpecialistCountService countService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid PageRequest page){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(aggregator.findAll(page));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getAllByFilter(@ModelAttribute @Valid SpecialistFilter filter){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(aggregator.findAllByFilter(filter));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countService.countAll());
    }
}
