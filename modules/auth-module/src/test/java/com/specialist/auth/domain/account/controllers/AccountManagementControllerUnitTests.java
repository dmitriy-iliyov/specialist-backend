package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.core.web.SessionCookieManager;
import com.specialist.auth.domain.access_token.models.AccessTokenUserDetails;
import com.specialist.auth.domain.account.models.dtos.AccountEmailUpdateDto;
import com.specialist.auth.domain.account.models.dtos.AccountPasswordUpdateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.services.AccountDeleteFacade;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.account.services.EmailUpdateService;
import com.specialist.auth.domain.role.Role;
import com.specialist.contracts.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountManagementControllerUnitTests {

    @Mock
    private AccountService service;
    @Mock
    private EmailUpdateService emailUpdateService;
    @Mock
    private AccountDeleteFacade deleteFacade;
    @Mock
    private SessionCookieManager sessionCookieManager;
    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AccountManagementController controller;

    @Test
    @DisplayName("UT: updatePassword() should call service and return OK")
    void updatePassword_shouldCallService() {
        UUID accountId = UUID.randomUUID();
        PrincipalDetails principal = mock(PrincipalDetails.class);
        when(principal.getAccountId()).thenReturn(accountId);
        
        AccountPasswordUpdateDto dto = new AccountPasswordUpdateDto("old", "new");
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(accountId, "email");

        when(service.updatePassword(dto)).thenReturn(responseDto);

        ResponseEntity<?> result = controller.updatePassword(principal, dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        assertEquals(accountId, dto.getId());
        verify(service).updatePassword(dto);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("UT: updateEmail() should call service and return OK")
    void updateEmail_shouldCallService() {
        UUID accountId = UUID.randomUUID();
        AccessTokenUserDetails principal = mock(AccessTokenUserDetails.class);
        when(principal.getAccountId()).thenReturn(accountId);
        when(principal.getRole()).thenReturn(Role.ROLE_USER); // Assuming Role.ROLE_USER maps to ProfileType.USER or similar logic

        AccountEmailUpdateDto dto = new AccountEmailUpdateDto("password", "new@email.com");
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(accountId, "new@email.com");

        when(emailUpdateService.update(dto)).thenReturn(responseDto);

        ResponseEntity<?> result = controller.updateEmail(principal, dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        assertEquals(accountId, dto.getId());
        // ProfileType.fromStringRole logic depends on implementation, assuming it works
        verify(emailUpdateService).update(dto);
        verifyNoMoreInteractions(emailUpdateService);
    }

    @Test
    @DisplayName("UT: delete() should call facades and return NO_CONTENT")
    void delete_shouldCallFacades() {
        UUID accountId = UUID.randomUUID();
        UUID tokenId = UUID.randomUUID();
        PrincipalDetails principal = mock(PrincipalDetails.class);
        when(principal.getAccountId()).thenReturn(accountId);
        when(principal.getId()).thenReturn(tokenId);

        ResponseEntity<?> result = controller.delete(principal, response);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteFacade).delete(accountId);
        verify(sessionCookieManager).terminate(tokenId, response);
        verifyNoMoreInteractions(deleteFacade, sessionCookieManager);
    }
}
