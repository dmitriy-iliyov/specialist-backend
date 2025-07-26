package com.aidcompass.user.controllers;

import com.aidcompass.user.PrincipalDetails;
import com.aidcompass.user.services.ProfileImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/users/me/avatar")
@RequiredArgsConstructor
public class PrivateAvatarController {

    private final ProfileImgService service;

    @PatchMapping
    public ResponseEntity<?> set(@AuthenticationPrincipal PrincipalDetails principal,
                                 @RequestPart("avatar") MultipartFile avatar) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.save(avatar, principal.getUserId()));
    }
}
