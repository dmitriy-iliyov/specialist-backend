package com.aidcompass.user.controllers;

import com.aidcompass.user.PrincipalDetails;
import com.aidcompass.user.models.dto.MemberCreateDto;
import com.aidcompass.user.models.dto.MemberUpdateDto;
import com.aidcompass.user.services.MemberOrchestrator;
import com.aidcompass.user.services.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/users/me")
@RequiredArgsConstructor
public class PrivateMemberController {

    private final MemberOrchestrator orchestrator;
    private final MemberService service;


    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestPart("user") @Valid MemberCreateDto dto,
                                    @RequestPart("avatar") MultipartFile avatar) {
        dto.setId(principal.getUserId());
        dto.setAvatar(avatar);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateById(principal.getUserId()));
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestPart("user") @Valid MemberUpdateDto dto,
                                    @RequestPart("avatar") MultipartFile avatar) {
        dto.setId(principal.getUserId());
        dto.setAvatar(avatar);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal) {
        service.deleteById(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}