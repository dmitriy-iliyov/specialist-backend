package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.DemodeRequest;
import com.specialist.auth.domain.account.models.dtos.DisableRequest;
import com.specialist.auth.domain.account.models.dtos.LockRequest;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.services.*;
import com.specialist.utils.validation.annotation.ValidUuid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/accounts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminAccountController {

    private final AccountPersistOrchestrator persistOrchestrator;
    private final AccountService defaultService;
    private final AdminAccountManagementService adminService;
    private final AdminAccountManagementFacade managementFacade;
    private final AccountDeleteOrchestrator deleteOrchestrator;

    public AdminAccountController(AccountPersistOrchestrator persistOrchestrator, AccountService defaultService,
                                  AdminAccountManagementService adminService, AdminAccountManagementFacade managementFacade,
                                  @Qualifier("adminAccountDeleteDecorator") AccountDeleteOrchestrator deleteOrchestrator) {
        this.persistOrchestrator = persistOrchestrator;
        this.defaultService = defaultService;
        this.adminService = adminService;
        this.managementFacade = managementFacade;
        this.deleteOrchestrator = deleteOrchestrator;
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_CREATE', 'ACCOUNT_MANAGER')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ManagedAccountCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(persistOrchestrator.save(dto));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@ModelAttribute @Valid AccountFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(defaultService.findAllByFilter(filter));
    }

    @PatchMapping("/{id}/demote")
    public ResponseEntity<?> demote(@PathVariable("id")
                                    @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id,
                                    @RequestBody @Valid DemodeRequest request) {
        request.setAccountId(id);
        managementFacade.demoteById(request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_LOCK', 'ACCOUNT_MANAGER')")
    @PatchMapping("/{id}/lock")
    public ResponseEntity<?> lock(@PathVariable("id")
                                  @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id,
                                  @RequestBody @Valid LockRequest request) {
        managementFacade.lockById(id, request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_LOCK', 'ACCOUNT_MANAGER')")
    @PatchMapping("/{id}/unlock")
    public ResponseEntity<?> unlock(@PathVariable("id")
                                    @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id) {
        adminService.unlockById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_DISABLE', 'ACCOUNT_MANAGER')")
    @PatchMapping("/{id}/disable")
    public ResponseEntity<?> disable(@PathVariable("id")
                                     @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id,
                                     @RequestBody @Valid DisableRequest request) {
        managementFacade.disableById(id, request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_DISABLE', 'ACCOUNT_MANAGER')")
    @PatchMapping("/{id}/enable")
    public ResponseEntity<?> enable(@PathVariable("id")
                                    @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id) {
        adminService.enableById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_DELETE', 'ACCOUNT_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")
                                    @ValidUuid(paramName = "id", message = "Id should have valid format.") UUID id) {
        deleteOrchestrator.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
