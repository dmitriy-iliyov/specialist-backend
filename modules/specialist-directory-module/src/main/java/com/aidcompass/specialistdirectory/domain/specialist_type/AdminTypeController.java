package com.aidcompass.specialistdirectory.domain.specialist_type;

import com.aidcompass.specialistdirectory.domain.specialist_type.services.ApproveTypeOrchestrator;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.TypeService;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeUpdateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/v1/types")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminTypeController {

    private final TypeService service;
    private final ApproveTypeOrchestrator orchestrator;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid TypeCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id")
                                    @NotNull(message = "Id is required.")
                                    @Positive(message = "Id should be positive.") Long id,
                                    @RequestBody @Valid TypeUpdateDto dto) {
        dto.setId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(dto));
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable("id")
                                     @NotNull(message = "Id is required.")
                                     @Positive(message = "Id should be positive.") Long id) {
        orchestrator.approve(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/unapproved")
    public ResponseEntity<?> getAllUnapproved() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllUnapproved());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")
                                    @NotNull(message = "Id is required.")
                                    @Positive(message = "Id should be positive.") Long id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
