package com.aidcompass.users.doctor.controllers;

import com.aidcompass.users.doctor.services.DoctorApprovalService;
import com.aidcompass.users.doctor.services.DoctorService;
import com.aidcompass.core.general.contracts.NotificationOrchestrator;
import com.aidcompass.core.general.contracts.dto.BaseSystemVolunteerDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/doctors")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminDoctorController {

    private final DoctorApprovalService approvalService;
    private final DoctorService service;
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
    public ResponseEntity<?> approveDoctor(@PathVariable("id") UUID id) {
        BaseSystemVolunteerDto dto = approvalService.approve(id);
        notificationOrchestrator.greeting(dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
