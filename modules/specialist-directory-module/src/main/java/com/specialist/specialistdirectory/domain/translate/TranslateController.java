package com.specialist.specialistdirectory.domain.translate;

import com.specialist.specialistdirectory.domain.translate.models.dtos.TranslateCreateDto;
import com.specialist.specialistdirectory.domain.translate.models.dtos.TranslateUpdateDto;
import com.specialist.specialistdirectory.domain.translate.services.TranslateOrchestrator;
import com.specialist.specialistdirectory.domain.translate.services.TranslateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/v1/types/{type_id}/translates")
@PreAuthorize("hasRole('ADMIN') && hasAuthority('TYPE_TRANSLATE_MANAGEMENT')")
@RequiredArgsConstructor
public class TranslateController {

    private final TranslateService service;
    private final TranslateOrchestrator orchestrator;

    @PostMapping
    public ResponseEntity<?> create(@PathVariable("type_id") @NotNull(message = "Type id is required.")
                                    @Positive(message = "Type id should be positive.") Long typeId,
                                    @RequestBody @Valid TranslateCreateDto dto) {
        dto.setTypeId(typeId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("type_id") @NotNull(message = "Type id is required.")
                                    @Positive(message = "Type id should be positive.") Long typeId,
                                    @PathVariable("id") @NotNull(message = "Id is required.")
                                    @Positive(message = "Id should be positive.") Long id,
                                    @RequestBody @Valid TranslateUpdateDto dto) {
        dto.setTypeId(typeId);
        dto.setId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("type_id") @NotNull(message = "Type id is required.")
                                    @Positive(message = "Type id should be positive.") Long typeId,
                                    @PathVariable("id") @NotNull(message = "Id is required.")
                                    @Positive(message = "Id should be positive.") Long id) {
        service.deleteById(id, typeId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}

