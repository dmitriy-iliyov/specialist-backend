package com.aidcompass.core.contact.core.controllers;

import com.aidcompass.core.contact.core.facades.GeneralContactOrchestrator;
import com.aidcompass.core.contact.core.models.dto.*;
import com.aidcompass.core.security.domain.token.models.TokenUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contacts")
@PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER', 'ROLE_JURIST', 'ROLE_DOCTOR')")
@RequiredArgsConstructor
public class ContactController {

    private final GeneralContactOrchestrator generalContactOrchestrator;


    @PostMapping
    public ResponseEntity<PrivateContactResponseDto> create(@AuthenticationPrincipal TokenUserDetails principal,
                                                            @RequestBody @Valid ContactCreateDto contact) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalContactOrchestrator.save(principal.getUserId(), contact));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PrivateContactResponseDto>> batchCreate(@AuthenticationPrincipal TokenUserDetails principal,
                                                                       @RequestBody
                                                                          @Valid ContactCreateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalContactOrchestrator.saveAll(principal.getUserId(), wrappedContacts.contacts()));
    }

    @PostMapping("/{contact_id}/confirmation-request")
    public ResponseEntity<?> requestConfirmation(@AuthenticationPrincipal TokenUserDetails principal,
                                                 @PathVariable("contact_id") Long contactId) {
        generalContactOrchestrator.requestConfirmation(principal.getUserId(), contactId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{contact_id}/link-email")
    public ResponseEntity<?> linkEmailToAccount(@AuthenticationPrincipal TokenUserDetails principal,
                                                @PathVariable("contact_id") Long id) {
        generalContactOrchestrator.markEmailAsLinkedToAccount(principal.getUserId(), id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/primary")
    public ResponseEntity<List<PublicContactResponseDto>> getPrimaryContacts(@AuthenticationPrincipal TokenUserDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalContactOrchestrator.findPrimaryByOwnerId(principal.getUserId()));
    }

    @GetMapping("/secondary/{owner_id}")
    public ResponseEntity<List<PublicContactResponseDto>> getSecondaryContacts(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalContactOrchestrator.findSecondaryByOwnerId(ownerId));
    }

    @GetMapping("/private")
    public ResponseEntity<List<PrivateContactResponseDto>> getPrivateContacts(@AuthenticationPrincipal TokenUserDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalContactOrchestrator.findAllPrivateByOwnerId(principal.getUserId()));
    }

    
    @GetMapping("/public/{owner_id}")
    public ResponseEntity<List<PublicContactResponseDto>> getPublicContacts(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalContactOrchestrator.findAllPublicByOwnerId(ownerId));
    }

    @PatchMapping
    public ResponseEntity<PrivateContactResponseDto> update(@AuthenticationPrincipal TokenUserDetails principal,
                                                            @RequestBody @Valid ContactUpdateDto contact) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalContactOrchestrator.update(principal.getUserId(), contact));
    }

    @PatchMapping("/batch")
    public ResponseEntity<List<PrivateContactResponseDto>> batchUpdate(@AuthenticationPrincipal TokenUserDetails principal,
                                                                       @RequestBody @Valid ContactUpdateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalContactOrchestrator.updateAll(principal.getUserId(), wrappedContacts.contacts()));
    }

    @DeleteMapping("/{contact_id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal TokenUserDetails principal,
                                    @PathVariable("contact_id") Long id) {
        generalContactOrchestrator.delete(principal.getUserId(), id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(@AuthenticationPrincipal TokenUserDetails principal) {
        generalContactOrchestrator.deleteAll(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}