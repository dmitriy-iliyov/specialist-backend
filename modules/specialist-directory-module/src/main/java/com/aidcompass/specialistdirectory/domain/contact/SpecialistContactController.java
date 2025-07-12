package com.aidcompass.specialistdirectory.domain.contact;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/contact")
public class SpecialistContactController {

    @GetMapping("/types")
    public ResponseEntity<?> getTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ContactType.values());
    }
}
