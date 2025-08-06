package com.aidcompass.user.controllers;

import com.aidcompass.contracts.auth.PrincipalDetails;
import com.aidcompass.user.services.UserOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users/me/avatar")
@RequiredArgsConstructor
public class PrivateAvatarController {

    private final UserOrchestrator orchestrator;

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> set(@AuthenticationPrincipal PrincipalDetails principal,
                                 @RequestPart("avatar") MultipartFile avatar) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(orchestrator.updateAvatar(avatar, principal.getUserId()));
    }
}
