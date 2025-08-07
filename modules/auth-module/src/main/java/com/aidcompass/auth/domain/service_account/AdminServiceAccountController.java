package com.aidcompass.auth.domain.service_account;

import com.aidcompass.auth.domain.access_token.models.AccessTokenUserDetails;
import com.aidcompass.auth.domain.service_account.models.ServiceAccountDto;
import com.aidcompass.utils.pagination.PageRequest;
import com.aidcompass.utils.validation.annotation.ValidUuid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/service-accounts")
@PreAuthorize("hasRole('ADMIN') && hasAuthority('SERVICE_ACCOUNT_MANAGER')")
@RequiredArgsConstructor
public class AdminServiceAccountController {

    private final ServiceAccountService service;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal AccessTokenUserDetails principal,
                                    @RequestBody @Valid ServiceAccountDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(principal.getUserId(), dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal AccessTokenUserDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id", message = "Id should be valid format.")
                                    String id,
                                    @RequestBody @Valid ServiceAccountDto dto) {
        dto.setId(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.save(principal.getUserId(), dto));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute @Valid PageRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal AccessTokenUserDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id", message = "Id should be valid format.")
                                    String id) {
        service.deleteById(UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
