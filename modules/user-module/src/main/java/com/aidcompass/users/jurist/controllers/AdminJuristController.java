package com.aidcompass.users.jurist.controllers;

import com.aidcompass.core.general.contracts.NotificationOrchestrator;
import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;
import com.aidcompass.users.jurist.services.JuristService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/jurists")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminJuristController {

    private final JuristService service;
    private final NotificationOrchestrator notificationOrchestrator;


    @GetMapping("/unapproved/count")
    public ResponseEntity<?> getUnapprovedCount() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.countByIsApproved(false));
    }

    @GetMapping("/unapproved")
    public ResponseEntity<?> getAllUnapproved(@RequestParam(value = "page", defaultValue = "0")
                                              @PositiveOrZero(message = "Page should be positive!")
                                              int page,
                                              @RequestParam(value = "size", defaultValue = "10")
                                              @Min(value = 10, message = "Size must be at least 10!")
                                              int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllUnapproved(page, size));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable("id") UUID id) {
        BaseSystemVolunteerDto dto = service.approve(id);
        notificationOrchestrator.greeting(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
