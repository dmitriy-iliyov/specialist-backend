package com.aidcompass.schedule.appointment;


import com.aidcompass.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.aidcompass.schedule.appointment.services.AppointmentOrchestrator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/appointments")
@RequiredArgsConstructor
public class AdminAppointmentController {

    private final AppointmentOrchestrator orchestrator;


    @PutMapping("/{customer_id}")
    public ResponseEntity<?> update(@PathVariable("customer_id") UUID customerId,
                                    @RequestBody @Valid AppointmentUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(customerId, dto));
    }
}