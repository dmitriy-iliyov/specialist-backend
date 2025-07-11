package com.aidcompass.core.security.domain.user.controllers;

import com.aidcompass.core.security.domain.user.models.dto.UserResponseDto;
import com.aidcompass.core.security.domain.user.models.dto.UserUpdateDto;
import com.aidcompass.core.security.domain.user.services.UserOrchestrator;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserOrchestrator userOrchestrator;


    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id")
                                 @UUID(message = "Invalid id format!") String id) {
        UserResponseDto userResponseDto = userOrchestrator.findById(java.util.UUID.fromString(id));
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id")
                                    @UUID(message = "Invalid id format!") String id,
                                    @RequestBody UserUpdateDto userUpdateDto,
                                    @RequestParam(value = "return_body", defaultValue = "false")
                                    boolean returnBody) {
        UserResponseDto response = userOrchestrator.update(java.util.UUID.fromString(id), userUpdateDto);
        if (returnBody) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")
                                    @UUID(message = "Invalid id format!") String id) {
        userOrchestrator.deleteById(java.util.UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}