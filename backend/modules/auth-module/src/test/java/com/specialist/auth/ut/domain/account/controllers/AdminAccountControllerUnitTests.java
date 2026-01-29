package com.specialist.auth.ut.domain.account.controllers;

import com.specialist.auth.domain.account.controllers.AdminAccountController;
import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.*;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.services.*;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminAccountControllerUnitTests {

    @Mock
    private AccountPersistFacade persistFacade;
    @Mock
    private AccountService defaultService;
    @Mock
    private AdminAccountManagementService adminService;
    @Mock
    private AdminAccountManagementFacade managementFacade;
    @Mock
    private AccountDeleteFacade deleteOrchestrator;

    @InjectMocks
    private AdminAccountController controller;

    @Test
    @DisplayName("UT: create() should call persistFacade and return CREATED")
    void create_shouldCallPersistFacade() {
        ManagedAccountCreateDto dto = new ManagedAccountCreateDto("email", "pass", Role.ROLE_USER, Collections.emptyList());
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(UUID.randomUUID(), "email");

        when(persistFacade.save(dto)).thenReturn(responseDto);

        ResponseEntity<?> result = controller.create(dto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(persistFacade).save(dto);
        verifyNoMoreInteractions(persistFacade);
    }

    @Test
    @DisplayName("UT: findAll() should call defaultService and return OK")
    void findAll_shouldCallDefaultService() {
        AccountFilter filter = new AccountFilter(0, 10, true, null, null, null, null);
        PageResponse<AccountResponseDto> pageResponse = new PageResponse<>(Collections.emptyList(), 0);

        when(defaultService.findAllByFilter(filter)).thenReturn(pageResponse);

        ResponseEntity<?> result = controller.findAll(filter);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(pageResponse, result.getBody());
        verify(defaultService).findAllByFilter(filter);
        verifyNoMoreInteractions(defaultService);
    }

    @Test
    @DisplayName("UT: demote() should call managementFacade and return NO_CONTENT")
    void demote_shouldCallManagementFacade() {
        UUID id = UUID.randomUUID();
        DemodeRequest request = new DemodeRequest();
        request.setAccountId(id);
        request.setAuthorities(Set.of(Authority.ACCOUNT_CREATE));

        ResponseEntity<?> result = controller.demote(id.toString(), request);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertEquals(id, request.getAccountId());
        verify(managementFacade).demoteById(request);
        verifyNoMoreInteractions(managementFacade);
    }

    @Test
    @DisplayName("UT: lock() should call managementFacade and return NO_CONTENT")
    void lock_shouldCallManagementFacade() {
        UUID id = UUID.randomUUID();
        LockRequest request = new LockRequest(LockReason.ABUSE, LocalDateTime.now());

        ResponseEntity<?> result = controller.lock(id.toString(), request);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(managementFacade).lockById(id, request);
        verifyNoMoreInteractions(managementFacade);
    }

    @Test
    @DisplayName("UT: unlock() should call adminService and return NO_CONTENT")
    void unlock_shouldCallAdminService() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> result = controller.unlock(id.toString());

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(adminService).unlockById(id);
        verifyNoMoreInteractions(adminService);
    }

    @Test
    @DisplayName("UT: disable() should call managementFacade and return NO_CONTENT")
    void disable_shouldCallManagementFacade() {
        UUID id = UUID.randomUUID();
        DisableRequest request = new DisableRequest(DisableReason.ATTACK_ATTEMPT_DETECTED);

        ResponseEntity<?> result = controller.disable(id.toString(), request);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(managementFacade).disableById(id, request);
        verifyNoMoreInteractions(managementFacade);
    }

    @Test
    @DisplayName("UT: enable() should call adminService and return NO_CONTENT")
    void enable_shouldCallAdminService() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> result = controller.enable(id.toString());

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(adminService).enableById(id);
        verifyNoMoreInteractions(adminService);
    }

    @Test
    @DisplayName("UT: delete() should call deleteOrchestrator and return NO_CONTENT")
    void delete_shouldCallDeleteOrchestrator() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> result = controller.delete(id.toString());

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(deleteOrchestrator).delete(id);
        verifyNoMoreInteractions(deleteOrchestrator);
    }
}
