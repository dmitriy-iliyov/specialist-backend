package com.specialist.specialistdirectory.domain.bookmark;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkCountService;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkPersistService;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkService;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.utils.validation.annotation.ValidUuid;
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
@PreAuthorize("hasAnyRole('USER', 'SPECIALIST')")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService service;
    private final BookmarkPersistService persistService;
    private final BookmarkCountService countService;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal PrincipalDetails principal,
                                    @RequestBody @Valid BookmarkCreateDto dto) {
        dto.setOwnerId(principal.getAccountId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(persistService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("id") @ValidUuid(paramName = "id") String id) {
        service.deleteById(principal.getAccountId(), UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal PrincipalDetails principal,
                                    @ModelAttribute @Valid ExtendedSpecialistFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByOwnerIdAndFilter(principal.getAccountId(), filter));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countService.countByOwnerId(principal.getAccountId()));
    }
}