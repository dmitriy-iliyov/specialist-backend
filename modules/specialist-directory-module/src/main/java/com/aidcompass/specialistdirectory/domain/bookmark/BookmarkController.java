package com.aidcompass.specialistdirectory.domain.bookmark;

import com.aidcompass.contracts.auth.PrincipalDetails;
import com.aidcompass.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.aidcompass.specialistdirectory.domain.bookmark.services.BookmarkCountService;
import com.aidcompass.specialistdirectory.domain.bookmark.services.BookmarkOrchestrator;
import com.aidcompass.specialistdirectory.domain.bookmark.services.BookmarkService;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.utils.pagination.PageRequest;
import com.aidcompass.utils.validation.annotation.ValidUuid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/me/bookmarks")
@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService service;
    private final BookmarkOrchestrator orchestrator;
    private final BookmarkCountService countService;


    @PostMapping
    public ResponseEntity<?> add(@AuthenticationPrincipal PrincipalDetails principal,
                                 @RequestBody @Valid BookmarkCreateDto dto) {
        dto.setOwnerId(principal.getUserId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orchestrator.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        service.deleteById(principal.getUserId(), UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal PrincipalDetails principal,
                                    @ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByOwnerId(principal.getUserId(), page));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getAllByFilter(@AuthenticationPrincipal PrincipalDetails principal,
                                            @ModelAttribute @Valid ExtendedSpecialistFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByOwnerIdAndFilter(principal.getUserId(), filter));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countService.countByOwnerId(principal.getUserId()));
    }
}