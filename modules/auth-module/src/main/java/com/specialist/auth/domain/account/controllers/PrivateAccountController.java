package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.core.SessionCookieManager;
import com.specialist.auth.domain.account.models.dtos.AccountEmailUpdateDto;
import com.specialist.auth.domain.account.models.dtos.AccountPasswordUpdateDto;
import com.specialist.auth.domain.account.services.AccountDeleteFacade;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.account.services.EmailUpdateFacade;
import com.specialist.contracts.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts/me")
@PreAuthorize("hasRole('USER')")
public class PrivateAccountController {

    private final AccountService service;
    private final EmailUpdateFacade emailUpdateFacade;
    private final AccountDeleteFacade deleteFacade;
    private final SessionCookieManager sessionCookieManager;

    public PrivateAccountController(AccountService service, EmailUpdateFacade emailUpdateFacade,
                                  @Qualifier("defaultAccountDeleteFacade") AccountDeleteFacade deleteFacade,
                                  SessionCookieManager sessionCookieManager) {
        this.service = service;
        this.emailUpdateFacade = emailUpdateFacade;
        this.deleteFacade = deleteFacade;
        this.sessionCookieManager = sessionCookieManager;
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal PrincipalDetails principal,
                                            @RequestBody @Valid AccountPasswordUpdateDto dto) {
        dto.setId(principal.getAccountId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updatePassword(dto));
    }

    @PatchMapping("/email")
    public ResponseEntity<?> updateEmail(@AuthenticationPrincipal PrincipalDetails principal,
                                         @RequestBody @Valid AccountEmailUpdateDto dto) {
        UUID accountId = principal.getAccountId();
        dto.setId(accountId);
        // FIXME: user still have auth-tokens in cookie when change email,
        //  this is ok while auth-tokens don't depends on user email
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(emailUpdateFacade.updateEmail(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal, HttpServletResponse response) {
        UUID accountId = principal.getAccountId();
        deleteFacade.delete(accountId);
        sessionCookieManager.terminate(principal.getId(), response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
