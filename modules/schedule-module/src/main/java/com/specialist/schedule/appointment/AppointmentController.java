package com.specialist.schedule.appointment;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.schedule.appointment.models.dto.AppointmentCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.schedule.appointment.models.dto.AppointmentUpdateDto;
import com.specialist.schedule.appointment.services.AppointmentFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/me/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentFacade facade;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/schedule")
    public ResponseEntity<?> schedule(@AuthenticationPrincipal PrincipalDetails principle,
                                      @RequestBody @Valid AppointmentCreateDto dto,
                                      @RequestParam(value = "return_body", defaultValue = "false")
                                      boolean returnBody){
        AppointmentResponseDto response = facade.save(principle.getAccountId(), dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyRole('USER', 'SPECIALIST')")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal,
                                 @PathVariable("id") @Positive(message = "Id should be positive!")
                                 Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.findById(principal.getAccountId(), id));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principle,
                                    @RequestBody @Valid AppointmentUpdateDto dto,
                                    @PathVariable("id") @Positive(message = "Id should be positive!")
                                    Long id,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody){
        dto.setId(id);
        AppointmentResponseDto response = facade.update(principle.getAccountId(), dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasRole('SPECIALIST')")
    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> complete(@AuthenticationPrincipal PrincipalDetails principle,
                                      @PathVariable("id") @Positive(message = "Id should be positive!")
                                      Long id,
                                      @RequestBody
                                      @NotBlank(message = "Review shouldn't be empty or blank!")
                                      @Size(max = 1000, message = "Review should be less then 1000 character!")
                                      String review) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.complete(principle.getAccountId(), id, review));
    }

    @PreAuthorize("hasAnyRole('USER', 'SPECIALIST')")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@AuthenticationPrincipal PrincipalDetails principle,
                                    @PathVariable("id")
                                    @Positive(message = "Id should be positive!")
                                    Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.cancel(principle.getAccountId(), id));
    }
}