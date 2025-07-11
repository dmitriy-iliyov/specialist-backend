package com.aidcompass.specialistdirectory.domain.specialist;

import com.aidcompass.specialistdirectory.domain.contact.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/specialists/info")
@RequiredArgsConstructor
public class SpecialistInfoController {


    @GetMapping("/contact-types")
    public ResponseEntity<?> getContactType() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ContactType.values());
    }
}
