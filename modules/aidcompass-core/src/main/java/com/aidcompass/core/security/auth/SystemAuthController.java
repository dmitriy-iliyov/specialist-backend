package com.aidcompass.core.security.auth;

import com.aidcompass.core.security.auth.services.SystemAuthService;
import com.aidcompass.core.security.auth.dto.ServiceAuthRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system/v1/auth")
@RequiredArgsConstructor
public class SystemAuthController {

    private final SystemAuthService service;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid ServiceAuthRequest requestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.login(requestDto));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestParam("service_name")
                                           @NotBlank(message = "Service name shouldn't be blank or empty!")
                                           String serviceName,
                                           @RequestParam(value = "days_ttl", defaultValue = "1")
                                           Integer daysTtl) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.generateToken(serviceName, daysTtl));
    }
}
