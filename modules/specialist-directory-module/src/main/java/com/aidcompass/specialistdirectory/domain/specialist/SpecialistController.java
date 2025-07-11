package com.aidcompass.specialistdirectory.domain.specialist;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.specialistdirectory.domain.specialist.services.SpecialistService;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist.services.SpecialistFacade;
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
@RequestMapping("/api/v1/specialists")
@RequiredArgsConstructor
public class SpecialistController {

    private final SpecialistService service;
    private final SpecialistFacade orchestrator;


    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid SpecialistCreateDto dto) {
        dto.setCreatorId(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(dto));
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal,
                                 @PathVariable("id") @ValidUuid String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(principal.getUserId(), UUID.fromString(id)));
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid String id,
                                    @RequestBody @Valid SpecialistUpdateDto dto) {
        dto.setCreatorId(principal.getUserId());
        dto.setId(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(dto));
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid String id) {

        orchestrator.delete(principal.getUserId(), UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/filter/city")
    public ResponseEntity<?> getAllByCity(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @GetMapping("/filter/type")
    public ResponseEntity<?> getAllByType(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

    @GetMapping("/filter/rating")
    public ResponseEntity<?> getAllByRating() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
