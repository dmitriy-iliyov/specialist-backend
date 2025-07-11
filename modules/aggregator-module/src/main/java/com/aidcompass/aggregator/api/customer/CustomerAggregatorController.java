package com.aidcompass.aggregator.api.customer;

import com.aidcompass.contracts.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aggregator/customers")
@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
@RequiredArgsConstructor
public class CustomerAggregatorController {

    private final CustomerAggregatorService service;


    @GetMapping("/me/profile")
    public ResponseEntity<?> getPrivateProfile(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateProfile(principal.getUserId()));
    }

    @DeleteMapping("/me/{password}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("password")
                                    @NotBlank(message = "Password can't be empty or blank!")
                                    @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
                                    String password,
                                    HttpServletRequest request, HttpServletResponse response) {
        service.delete(principal.getUserId(), password, request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
