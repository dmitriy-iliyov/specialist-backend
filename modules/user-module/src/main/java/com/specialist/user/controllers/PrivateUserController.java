package com.specialist.user.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.user.models.dtos.UserCreateDto;
import com.specialist.user.models.dtos.UserUpdateDto;
import com.specialist.user.services.UserOrchestrator;
import com.specialist.user.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users/me")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class PrivateUserController {

    private final UserOrchestrator orchestrator;
    private final UserService service;
    private final ObjectMapper mapper;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestPart("user") String rawDto,
                                    @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws JsonProcessingException {
        UserCreateDto dto = mapper.readValue(rawDto, UserCreateDto.class);
        dto.setId(principal.getAccountId());
        dto.setAvatar(avatar);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateById(principal.getAccountId()));
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestPart("user") String rawDto,
                                    @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws JsonProcessingException {
        UserUpdateDto dto = mapper.readValue(rawDto, UserUpdateDto.class);
        dto.setId(principal.getAccountId());
        dto.setAvatar(avatar);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal, HttpServletResponse response) {
        orchestrator.delete(principal.getAccountId(), principal.getId(), response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}