package com.aidcompass.specialistdirectory.domain.bookmark;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.specialistdirectory.domain.bookmark.services.BookmarkCountService;
import com.aidcompass.specialistdirectory.domain.bookmark.services.BookmarkService;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.validation.ValidUuid;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/specialists")
@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService service;
    private final BookmarkCountService countService;


    @PostMapping("/bookmarks/{specialists_id}")
    public ResponseEntity<?> add(@AuthenticationPrincipal PrincipalDetails principal,
                                 @PathVariable("specialists_id") @ValidUuid String specialistsId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(principal.getUserId(), UUID.fromString(specialistsId)));
    }

    @DeleteMapping("/bookmarks/{specialists_id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("specialists_id") @ValidUuid String specialistsId) {
        service.deleteBySpecialistId(principal.getUserId(), UUID.fromString(specialistsId));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<?> getAll(@AuthenticationPrincipal PrincipalDetails principal,
                                    @ModelAttribute @Valid PageRequest page) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByOwnerId(principal.getUserId(), page));
    }

    @GetMapping("/bookmarks/filter")
    public ResponseEntity<?> getAllByFilter(@AuthenticationPrincipal PrincipalDetails principal,
                                            @ModelAttribute @Valid ExtendedSpecialistFilter filter) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByOwnerIdAndFilter(principal.getUserId(), filter));
    }

    @GetMapping("/bookmarks/count")
    public ResponseEntity<?> count(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(countService.countByOwnerId(principal.getUserId()));
    }
}
