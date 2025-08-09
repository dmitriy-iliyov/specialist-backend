package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.*;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.models.enums.UnableReason;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.account.services.PersistAccountOrchestrator;
import com.specialist.utils.pagination.PageRequest;
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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminAccountControllerUnitTests {

    @Mock
    PersistAccountOrchestrator orchestrator;

    @Mock
    AccountService service;

    @InjectMocks
    AdminAccountController controller;

    @Test
    @DisplayName("UT: create() when dto valid should return 201")
    void create_whenDtoValid_shouldReturn201() {
        ManagedAccountCreateDto dto = new ManagedAccountCreateDto("test@mail.com", "securepass123", "ADMIN", List.of("ACCOUNT_CREATE"));
        ShortAccountResponseDto expected = new ShortAccountResponseDto(UUID.randomUUID(), "test@mail.com", LocalDateTime.now());

        when(orchestrator.save(dto)).thenReturn(expected);

        ResponseEntity<?> response = controller.create(dto);

        verify(orchestrator, times(1)).save(dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    @DisplayName("UT: create() when service throws should throw exception")
    void create_whenInvalid_shouldThrowException() {
        ManagedAccountCreateDto dto = new ManagedAccountCreateDto("mail@mail.com", "pass", "USER", List.of());

        when(orchestrator.save(dto)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.create(dto));
    }

    @Test
    @DisplayName("UT: findAll() when valid should return 200")
    void findAll_whenValid_shouldReturn200() {
        PageRequest request = new PageRequest(0, 10, true);
        PageResponse<AccountResponseDto> expected = new PageResponse<>(List.of(), 0);

        when(service.findAll(request)).thenReturn(expected);

        ResponseEntity<?> response = controller.findAll(request);

        verify(service, times(1)).findAll(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    @DisplayName("UT: findAll() when service throws should throw exception")
    void findAll_whenInvalid_shouldThrowException() {
        PageRequest request = new PageRequest(0, 10, true);

        when(service.findAll(request)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.findAll(request));
    }

    @Test
    @DisplayName("UT: findAllByFilter() when valid should return 200")
    void findAllByFilter_whenValid_shouldReturn200() {
        AccountFilter filter = new AccountFilter(true, "USER_REQUEST", false,
                "PASSWORD_EXPIRED",  0, 20, true
        );
        PageResponse<AccountResponseDto> expected = new PageResponse<>(List.of(), 0);

        when(service.findAllByFilter(filter)).thenReturn(expected);

        ResponseEntity<?> response = controller.findAllByFilter(filter);

        verify(service, times(1)).findAllByFilter(filter);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    @DisplayName("UT: findAllByFilter() when service throws should throw exception")
    void findAllByFilter_whenInvalid_shouldThrowException() {
        AccountFilter filter = new AccountFilter(true, "USER_REQUEST", false,
                "PASSWORD_EXPIRED",  0, 20, true
        );

        when(service.findAllByFilter(filter)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.findAllByFilter(filter));
    }

    @Test
    @DisplayName("UT: lock() when valid should return 204")
    void lock_whenValid_shouldReturn204() {
        UUID id = UUID.randomUUID();
        LockRequest request = new LockRequest(LockReason.ABUSE, LocalDateTime.now());

        ResponseEntity<?> response = controller.lock(id, request);

        verify(service, times(1)).lockById(id, request);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("UT: lock() when service throws should throw exception")
    void lock_whenInvalid_shouldThrowException() {
        UUID id = UUID.randomUUID();
        LockRequest request = new LockRequest(LockReason.ABUSE, LocalDateTime.now());

        doThrow(RuntimeException.class).when(service).lockById(id, request);

        assertThrows(RuntimeException.class, () -> controller.lock(id, request));
    }

    @Test
    @DisplayName("UT: unable() when valid should return 204")
    void unable_whenValid_shouldReturn204() {
        UUID id = UUID.randomUUID();
        UnableRequest request = new UnableRequest(UnableReason.PERMANENTLY_ABUSE);

        ResponseEntity<?> response = controller.unable(id, request);

        verify(service, times(1)).setUnableById(id, request);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("UT: unable() when service throws should throw exception")
    void unable_whenInvalid_shouldThrowException() {
        UUID id = UUID.randomUUID();
        UnableRequest request = new UnableRequest(UnableReason.PERMANENTLY_ABUSE);

        doThrow(RuntimeException.class).when(service).setUnableById(id, request);

        assertThrows(RuntimeException.class, () -> controller.unable(id, request));
    }

    @Test
    @DisplayName("UT: delete() when valid should return 204")
    void delete_whenValid_shouldReturn204() {
        UUID id = UUID.randomUUID();

        ResponseEntity<?> response = controller.delete(id);

        verify(service, times(1)).deleteById(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("UT: delete() when service throws should throw exception")
    void delete_whenInvalid_shouldThrowException() {
        UUID id = UUID.randomUUID();

        doThrow(RuntimeException.class).when(service).deleteById(id);

        assertThrows(RuntimeException.class, () -> controller.delete(id));
    }
}