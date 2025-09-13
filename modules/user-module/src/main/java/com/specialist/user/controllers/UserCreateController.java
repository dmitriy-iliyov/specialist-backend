package com.specialist.user.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.contracts.user.UserType;
import com.specialist.user.models.dtos.CreateRequest;
import com.specialist.user.services.UserPersistOrchestrator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('UNCOMPLETED_USER')")
@RequiredArgsConstructor
public class UserCreateController {

    private final UserPersistOrchestrator orchestrator;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestParam("type") UserType type,
                                    @RequestPart("user") String rawDto,
                                    @RequestPart(value = "avatar", required = false) MultipartFile avatar,
                                    HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(
                        new CreateRequest(principal.getAccountId(), type, rawDto, avatar, request, response))
                );
    }

}
