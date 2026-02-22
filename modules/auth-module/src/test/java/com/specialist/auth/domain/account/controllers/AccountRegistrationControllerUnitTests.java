package com.specialist.auth.domain.account.controllers;

import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.services.AccountPersistFacade;
import com.specialist.auth.domain.role.Role;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountRegistrationControllerUnitTests {

    @Mock
    private AccountPersistFacade persistFacade;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AccountRegistrationController controller;

    @Test
    @DisplayName("UT: register() should call facade and return CREATED status")
    void register_shouldCallFacadeAndReturnCreated() {
        DefaultAccountCreateDto dto = new DefaultAccountCreateDto("test@example.com", "password", Role.ROLE_USER, Collections.emptyList());
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(UUID.randomUUID(), "test@example.com");

        when(persistFacade.save(dto, response)).thenReturn(responseDto);

        ResponseEntity<?> result = controller.register(dto, response);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(persistFacade).save(dto, response);
        verifyNoMoreInteractions(persistFacade);
    }
}
