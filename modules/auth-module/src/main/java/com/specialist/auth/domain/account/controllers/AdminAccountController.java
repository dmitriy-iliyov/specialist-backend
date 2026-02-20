package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.DemodeRequest;
import com.specialist.auth.domain.account.models.dtos.DisableRequest;
import com.specialist.auth.domain.account.models.dtos.LockRequest;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.services.*;
import com.specialist.utils.uuid.UUIDv7;
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

    private final AccountPersistFacade persistFacade;
    private final AccountService defaultService;
    private final AdminAccountManagementService adminService;
    private final AdminAccountManagementFacade managementFacade;
    private final AccountDeleteFacade deleteOrchestrator;

    public AdminAccountController(AccountPersistFacade persistFacade, AccountService defaultService,
                                  AdminAccountManagementService adminService, AdminAccountManagementFacade managementFacade,
                                  @Qualifier("adminAccountDeleteDecorator") AccountDeleteFacade deleteOrchestrator) {
        this.persistFacade = persistFacade;
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
                .body(persistFacade.save(dto));
    }

    @GetMapping
    public ResponseEntity<?> findAll(@ModelAttribute @Valid AccountFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(defaultService.findAllByFilter(filter));
    }

    @PatchMapping("/{id}/demote")
    public ResponseEntity<?> demote(@PathVariable("id")
                                    @UUIDv7(paramName = "id", message = "Id should have valid format.") String id,
                                    @RequestBody @Valid DemodeRequest request) {
        request.setAccountId(UUID.fromString(id));
        managementFacade.demoteById(request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_LOCK', 'ACCOUNT_MANAGER')")
    @PatchMapping("/{id}/lock")
    public ResponseEntity<?> lock(@PathVariable("id")
                                  @UUIDv7(paramName = "id", message = "Id should have valid format.") String id,
                                  @RequestBody @Valid LockRequest request) {
        managementFacade.lockById(UUID.fromString(id), request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_LOCK', 'ACCOUNT_MANAGER')")
    @PatchMapping("/{id}/unlock")
    public ResponseEntity<?> unlock(@PathVariable("id")
                                    @UUIDv7(paramName = "id", message = "Id should have valid format.") String id) {
        adminService.unlockById(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_DISABLE', 'ACCOUNT_MANAGER')")
    @PatchMapping("/{id}/disable")
    public ResponseEntity<?> disable(@PathVariable("id")
                                     @UUIDv7(paramName = "id", message = "Id should have valid format.") String id,
                                     @RequestBody @Valid DisableRequest request) {
        managementFacade.disableById(UUID.fromString(id), request);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_DISABLE', 'ACCOUNT_MANAGER')")
    @PatchMapping("/{id}/enable")
    public ResponseEntity<?> enable(@PathVariable("id")
                                    @UUIDv7(paramName = "id", message = "Id should have valid format.") String id) {
        adminService.enableById(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ACCOUNT_DELETE', 'ACCOUNT_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")
                                    @UUIDv7(paramName = "id", message = "Id should have valid format.") String id) {
        deleteOrchestrator.delete(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
