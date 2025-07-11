package com.aidcompass.specialistdirectory.domain.specialist;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist.services.SpecialistService;
import com.aidcompass.specialistdirectory.utils.validation.ValidUuid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/specialists")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminSpecialistController {

    private final SpecialistService service;


    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid SpecialistCreateDto dto) {
        dto.setCreatorId(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") @ValidUuid String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(UUID.fromString(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @ValidUuid String id,
                                    @RequestBody @Valid SpecialistUpdateDto dto) {
        dto.setId(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @ValidUuid String id) {

        service.delete(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
