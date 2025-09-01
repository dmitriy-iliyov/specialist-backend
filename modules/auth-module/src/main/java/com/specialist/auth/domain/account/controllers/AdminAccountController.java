package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.DemodeRequest;
import com.specialist.auth.domain.account.models.dtos.DisableRequest;
import com.specialist.auth.domain.account.models.dtos.LockRequest;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.services.AccountDeleteOrchestrator;
import com.specialist.auth.domain.account.services.AccountPersistOrchestrator;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.account.services.AdminAccountOrchestrator;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.validation.annotation.ValidUuid;
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

    private final AccountPersistOrchestrator persistOrchestrator;
    private final AccountService service;
    private final AdminAccountOrchestrator adminOrchestrator;
    private final AccountDeleteOrchestrator deleteOrchestrator;

    @PreAuthorize("hasAnyAuthority('ACCOUNT_CREATE', 'ACCOUNT_MANAGER')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ManagedAccountCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(persistOrchestrator.save(dto));
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

    @PostMapping("/{id}/demote")
    public ResponseEntity<?> demote(@PathVariable("id")
                                    @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id,
                                    @RequestBody @Valid DemodeRequest request) {
        request.setAccountId(id);
        adminOrchestrator.demoteById(request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_LOCK', 'ACCOUNT_MANAGER')")
    @PostMapping("/{id}/lock")
    public ResponseEntity<?> lock(@PathVariable("id")
                                  @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id,
                                  @RequestBody @Valid LockRequest request) {
        adminOrchestrator.lockById(id, request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_LOCK', 'ACCOUNT_MANAGER')")
    @PostMapping("/{id}/unlock")
    public ResponseEntity<?> unlock(@PathVariable("id")
                                    @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id) {
        service.unlockById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_DISABLE', 'ACCOUNT_MANAGER')")
    @PostMapping("/{id}/disable")
    public ResponseEntity<?> disable(@PathVariable("id")
                                    @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id,
                                     @RequestBody @Valid DisableRequest request) {
        adminOrchestrator.disableById(id, request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_DISABLE', 'ACCOUNT_MANAGER')")
    @PostMapping("/{id}/enable")
    public ResponseEntity<?> enable(@PathVariable("id")
                                    @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id) {
        service.enableById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_DELETE', 'ACCOUNT_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        deleteOrchestrator.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
