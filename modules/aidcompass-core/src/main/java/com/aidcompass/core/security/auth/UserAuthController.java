package com.aidcompass.core.security.auth;

import com.aidcompass.core.security.auth.dto.AuthRequest;
import com.aidcompass.core.security.auth.services.UserAuthService;
import com.aidcompass.core.security.domain.token.models.TokenUserDetails;
import com.aidcompass.core.security.domain.user.services.UserService;
import com.aidcompass.core.security.exceptions.not_found.UserNotFoundByIdException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService authService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest requestDto,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        authService.login(requestDto, request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_CUSTOMER', 'ROLE_DOCTOR', 'ROLE_JURIST', 'ROLE_ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal TokenUserDetails principal) {
        if (userService.existsById(principal.getUserId())) {
            return ResponseEntity.ok()
                    .body(Map.of("authority", principal.getAuthorities().stream().findFirst().get().getAuthority()));
        } else {
            throw new UserNotFoundByIdException();
        }
    }
}
