package com.specialist.auth.ut.domain.account.services;


import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.models.events.AccountCreateEvent;
import com.specialist.auth.domain.account.services.AccountPersistFacadeImpl;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.infrastructure.message.services.ConfirmationService;
import com.specialist.contracts.profile.SystemProfilePersistService;
import com.specialist.contracts.profile.dto.ShortProfileCreateDto;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountPersistFacadeImplUnitTests {

    @Mock
    AccountService accountService;

    @Mock
    ConfirmationService confirmationService;

    @Mock
    SystemProfilePersistService persistService;

    @Mock
    ApplicationEventPublisher eventPublisher;

    @InjectMocks
    AccountPersistFacadeImpl orchestrator;

    @Test
    @DisplayName("UT: save(DefaultAccountCreateDto) sets role, authorities and sends confirmation code")
    void save_withDefaultAccountCreateDto_setsRoleAuthoritiesAndSendsConfirmation() {
        var dto = new DefaultAccountCreateDto("test@example.com", "pass");

        var expectedResponse = new ShortAccountResponseDto(UUID.randomUUID(), "admin@example.com", LocalDateTime.now());
        when(accountService.save(any(DefaultAccountCreateDto.class))).thenReturn(expectedResponse);
        doNothing().when(persistService).save(any(ShortProfileCreateDto.class));
        doNothing().when(eventPublisher).publishEvent(any(AccountCreateEvent.class));

        var responseMock = mock(HttpServletResponse.class);

        var actual = orchestrator.save(dto, responseMock);

        assertEquals(Role.ROLE_UNCOMPLETED_USER, dto.getRole());
        assertEquals(List.of(), dto.getAuthorities());

        verify(accountService).save(dto);
        verify(persistService, times(1)).save(any(ShortProfileCreateDto.class));
        verify(eventPublisher, times(1)).publishEvent(any(AccountCreateEvent.class));
        assertSame(expectedResponse, actual);
    }

    @Test
    @DisplayName("UT: save(ManagedAccountCreateDto) maps DTO correctly and sends confirmation code")
    void save_withManagedAccountCreateDto_mapsAndSendsConfirmation() {
        var dto = new ManagedAccountCreateDto(
                "admin@example.com",
                "adminpass", Role.ROLE_ADMIN,
                List.of(Authority.SPECIALIST_CREATE, Authority.REVIEW_CREATE_UPDATE)
        );

        var expectedResponse = new ShortAccountResponseDto(UUID.randomUUID(), "admin@example.com", LocalDateTime.now());
        when(accountService.save(any(DefaultAccountCreateDto.class))).thenReturn(expectedResponse);
        doNothing().when(persistService).save(any(ShortProfileCreateDto.class));
        doNothing().when(eventPublisher).publishEvent(any(AccountCreateEvent.class));
        var actual = orchestrator.save(dto);

        var captor = ArgumentCaptor.forClass(DefaultAccountCreateDto.class);
        verify(accountService).save(captor.capture());

        var savedDto = captor.getValue();
        assertEquals("admin@example.com", savedDto.getEmail());
        assertEquals("adminpass", savedDto.getPassword());
        assertEquals(Role.ROLE_ADMIN, savedDto.getRole());
        assertEquals(List.of(
                Authority.SPECIALIST_CREATE,
                Authority.REVIEW_CREATE_UPDATE), savedDto.getAuthorities());
        assertSame(expectedResponse, actual);
    }

    @Test
    @DisplayName("UT: save(DefaultAccountCreateDto) when dto invalid should throw IllegalArgumentException")
    void save_withInvalidDefaultAccountCreateDto_shouldThrow() {
        var dto = new DefaultAccountCreateDto(null, ""); // неверный email и пустой пароль, например

        when(accountService.save(any(DefaultAccountCreateDto.class))).thenThrow(new IllegalArgumentException("Invalid data"));

        var responseMock = mock(HttpServletResponse.class);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> orchestrator.save(dto, responseMock));

        assertEquals("Invalid data", ex.getMessage());

        verify(confirmationService, never()).sendConfirmationCode(anyString());
    }
}