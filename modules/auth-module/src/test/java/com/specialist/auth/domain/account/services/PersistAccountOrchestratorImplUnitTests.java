package com.specialist.auth.domain.account.services;


import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.infrastructure.message.services.ConfirmationService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersistAccountOrchestratorImplUnitTests {

    @Mock
    AccountService accountService;

    @Mock
    ConfirmationService confirmationService;

    @InjectMocks
    PersistAccountOrchestratorImpl orchestrator;

    @Test
    @DisplayName("UT: save(DefaultAccountCreateDto) sets role, authorities and sends confirmation code")
    void save_withDefaultAccountCreateDto_setsRoleAuthoritiesAndSendsConfirmation() {
        var dto = new DefaultAccountCreateDto("test@example.com", "pass");

        var expectedResponse = new ShortAccountResponseDto(UUID.randomUUID(), "admin@example.com", LocalDateTime.now());
        when(accountService.save(any())).thenReturn(expectedResponse);

        var responseMock = mock(HttpServletResponse.class);

        var actual = orchestrator.save(dto, responseMock);

        assertEquals(Role.ROLE_USER, dto.getRole());
        assertEquals(List.of(
                Authority.SPECIALIST_CREATE_UPDATE,
                Authority.REVIEW_CREATE_UPDATE,
                Authority.TYPE_SUGGEST), dto.getAuthorities());

        verify(accountService).save(dto);
        verify(confirmationService).sendConfirmationCode("test@example.com");

        assertSame(expectedResponse, actual);
    }

    @Test
    @DisplayName("UT: save(ManagedAccountCreateDto) maps DTO correctly and sends confirmation code")
    void save_withManagedAccountCreateDto_mapsAndSendsConfirmation() {
        var dto = new ManagedAccountCreateDto(
                "admin@example.com",
                "adminpass",
                "ROLE_ADMIN",
                List.of("SPECIALIST_CREATE_UPDATE", "REVIEW_CREATE_UPDATE")
        );

        var expectedResponse = new ShortAccountResponseDto(UUID.randomUUID(), "admin@example.com", LocalDateTime.now());
        when(accountService.save(any())).thenReturn(expectedResponse);

        var actual = orchestrator.save(dto);

        var captor = ArgumentCaptor.forClass(DefaultAccountCreateDto.class);
        verify(accountService).save(captor.capture());

        var savedDto = captor.getValue();
        assertEquals("admin@example.com", savedDto.getEmail());
        assertEquals("adminpass", savedDto.getPassword());
        assertEquals(Role.ROLE_ADMIN, savedDto.getRole());
        assertEquals(List.of(
                Authority.SPECIALIST_CREATE_UPDATE,
                Authority.REVIEW_CREATE_UPDATE), savedDto.getAuthorities());

        verify(confirmationService).sendConfirmationCode("admin@example.com");

        assertSame(expectedResponse, actual);
    }

    @Test
    @DisplayName("UT: save(DefaultAccountCreateDto) when dto invalid should throw IllegalArgumentException")
    void save_withInvalidDefaultAccountCreateDto_shouldThrow() {
        var dto = new DefaultAccountCreateDto(null, ""); // неверный email и пустой пароль, например

        when(accountService.save(any())).thenThrow(new IllegalArgumentException("Invalid data"));

        var responseMock = mock(HttpServletResponse.class);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> orchestrator.save(dto, responseMock));

        assertEquals("Invalid data", ex.getMessage());

        verify(confirmationService, never()).sendConfirmationCode(anyString());
    }
}