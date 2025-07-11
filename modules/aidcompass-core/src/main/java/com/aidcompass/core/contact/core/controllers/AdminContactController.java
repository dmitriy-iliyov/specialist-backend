package com.aidcompass.core.contact.core.controllers;

import com.aidcompass.core.contact.core.facades.GeneralContactOrchestrator;
import com.aidcompass.core.contact.core.models.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/{owner_id}/contacts")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminContactController {

    private final GeneralContactOrchestrator generalContactOrchestrator;


    @PostMapping
    public ResponseEntity<PrivateContactResponseDto> create(@PathVariable("owner_id") UUID ownerId,
                                                            @RequestBody @Valid ContactCreateDto contact) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalContactOrchestrator.save(ownerId, contact));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<PrivateContactResponseDto>> batchCreate(@PathVariable("owner_id") UUID ownerId,
                                                                       @RequestBody
                                                                       @Valid ContactCreateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(generalContactOrchestrator.saveAll(ownerId, wrappedContacts.contacts()));
    }

    @PatchMapping("/{contact_id}/link-email")
    public ResponseEntity<?> linkEmailToAccount(@PathVariable("owner_id") UUID ownerId,
                                                @PathVariable("contact_id") Long id) {
        generalContactOrchestrator.markEmailAsLinkedToAccount(ownerId, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/private")
    public ResponseEntity<List<PrivateContactResponseDto>> getPrivateContacts(@PathVariable("owner_id") UUID ownerId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalContactOrchestrator.findAllPrivateByOwnerId(ownerId));
    }

    @PatchMapping
    public ResponseEntity<PrivateContactResponseDto> update(@PathVariable("owner_id") UUID ownerId,
                                                            @RequestBody @Valid ContactUpdateDto contact) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalContactOrchestrator.update(ownerId, contact));
    }

    @PutMapping("/batch")
    public ResponseEntity<List<PrivateContactResponseDto>> batchUpdate(@PathVariable("owner_id") UUID ownerId,
                                                                       @RequestBody
                                                                       @Valid ContactUpdateDtoList wrappedContacts) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(generalContactOrchestrator.updateAll(ownerId, wrappedContacts.contacts()));
    }

    @DeleteMapping("/{contact_id}")
    public ResponseEntity<?> delete(@PathVariable("owner_id") UUID ownerId,
                                    @PathVariable("contact_id") Long contactId) {
        generalContactOrchestrator.delete(ownerId, contactId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(@PathVariable("owner_id") UUID ownerId) {
        generalContactOrchestrator.deleteAll(ownerId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
