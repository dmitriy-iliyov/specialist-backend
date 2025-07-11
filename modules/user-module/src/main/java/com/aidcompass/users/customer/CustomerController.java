package com.aidcompass.users.customer;


import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.users.customer.models.CustomerDto;
import com.aidcompass.users.customer.models.PrivateCustomerResponseDto;
import com.aidcompass.users.customer.models.PublicCustomerResponseDto;
import com.aidcompass.users.customer.services.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
    private final CustomerPersistFacade facade;


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/empty")
    public ResponseEntity<?> createEmpty(@AuthenticationPrincipal PrincipalDetails principal) {
        facade.save(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid CustomerDto dto,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody, HttpServletResponse response) {
        PrivateCustomerResponseDto responseDto = facade.save(principal.getUserId(), dto, response);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(responseDto);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_DOCTOR', 'ROLE_CUSTOMER', 'ROLE_JURIST')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPublic(@PathVariable("id") UUID id) {
        PublicCustomerResponseDto publicCustomerResponseDto = service.findPublicById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(publicCustomerResponseDto);
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @GetMapping("/me")
    public ResponseEntity<?> getPrivate(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateById(principal.getUserId()));
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PatchMapping("/me")
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid CustomerDto dto,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody) {
        PrivateCustomerResponseDto responseDto = service.updateById(principal.getUserId(), dto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(responseDto);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @DeleteMapping("/me")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal) {
        service.deleteById(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.count());
    }
}
