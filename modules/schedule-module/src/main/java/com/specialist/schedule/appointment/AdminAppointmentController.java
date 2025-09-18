package com.specialist.schedule.appointment;


import com.specialist.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.specialist.schedule.appointment.services.AppointmentOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/appointments")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminAppointmentController {

    private final AppointmentOrchestrator orchestrator;

    @PutMapping("/{user_id}")
    public ResponseEntity<?> update(@PathVariable("user_id") UUID userId,
                                    @RequestBody @Valid AppointmentUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(userId, dto));
    }
}