package com.aidcompass.auth.domain.account.controllers;

import com.aidcompass.auth.domain.account.models.AccountFilter;
import com.aidcompass.auth.domain.account.models.dtos.LockRequest;
import com.aidcompass.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.aidcompass.auth.domain.account.models.dtos.UnableRequest;
import com.aidcompass.auth.domain.account.services.PersistAccountOrchestrator;
import com.aidcompass.auth.domain.account.services.AccountService;
import com.aidcompass.utils.pagination.PageRequest;
import com.aidcompass.utils.validation.annotation.ValidUuid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/accounts")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminAccountController {

    private final PersistAccountOrchestrator orchestrator;
    private final AccountService service;

    @PreAuthorize("hasAuthority('ACCOUNT_CREATE')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ManagedAccountCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@ModelAttribute @Valid PageRequest pageRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll(pageRequest));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> findAllByFilter(@ModelAttribute @Valid AccountFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByFilter(filter));
    }

    @PreAuthorize("hasAuthority('ACCOUNT_LOCK')")
    @PostMapping("/{id}/lock")
    public ResponseEntity<?> lock(@PathVariable("id")
                                  @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id,
                                  @RequestBody @Valid LockRequest request) {
        service.lockById(id, request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('ACCOUNT_UNABLE')")
    @PostMapping("/{id}/unable")
    public ResponseEntity<?> unable(@PathVariable("id")
                                    @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id,
                                    @RequestBody @Valid UnableRequest request) {
        service.setUnableById(id, request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('ACCOUNT_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
