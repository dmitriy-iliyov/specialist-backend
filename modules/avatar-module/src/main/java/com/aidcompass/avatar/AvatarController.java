package com.aidcompass.avatar;

import com.aidcompass.contracts.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/avatars")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_CUSTOMER', 'ROLE_DOCTOR', 'ROLE_JURIST')")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService service;


    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> set(@AuthenticationPrincipal PrincipalDetails principal,
                                 @RequestParam("image") MultipartFile image) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.saveOrUpdate(principal.getUserId(), image));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/default", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> setDefault(@RequestParam("image") MultipartFile image) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.saveOrUpdateDefault(image));
    }

    @PreAuthorize("permitAll()")
    @GetMapping( "/{user_id}")
    public ResponseEntity<String> getUrl(@PathVariable("user_id") UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findUrlByUserId(userId));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal) {
        service.delete(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
