package com.specialist.auth.ut.domain.account.controllers;

import com.specialist.auth.domain.account.controllers.AccountRegistrationController;
import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.services.AccountPersistFacade;
import com.specialist.auth.domain.account.services.AccountService;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountRegistrationControllerUnitTests {

    @Mock
    AccountPersistFacade orchestrator;

    @Mock
    AccountService service;

    @Mock
    HttpServletResponse response;

    @Mock
    PrincipalDetails principalDetails;

    @InjectMocks
    AccountRegistrationController accountRegistrationController;

    @Test
    @DisplayName("UT: create() when dto valid should return 201")
    public void create_whenDtoValid_shouldReturn201() {
        DefaultAccountCreateDto createDto = new DefaultAccountCreateDto("email@gmail.com", "securepassword");

        UUID fixedId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        LocalDateTime fixedTime = LocalDateTime.of(2023, 1, 1, 12, 0);
        ShortAccountResponseDto expectedResponse = new ShortAccountResponseDto(fixedId, "email@gmail.com", fixedTime);

        when(orchestrator.save(eq(createDto), any())).thenReturn(expectedResponse);

        ResponseEntity<?> responseEntity = accountRegistrationController.register(createDto, response);

        verify(orchestrator, times(1)).save(eq(createDto), any());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("UT: create() when dto invalid should return 400")
    public void create_whenDtoInvalid_shouldReturn400() {
        DefaultAccountCreateDto createDto = new DefaultAccountCreateDto("email@gmail.com", "securepassword");

        when(orchestrator.save(eq(createDto), any())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> accountRegistrationController.register(createDto, response));

        verify(orchestrator, times(1)).save(eq(createDto), any());
    }

//    @Test
//    @DisplayName("UT: updatePassword() when dto valid should return 200")
//    public void updatePassword_whenDtoValid_shouldReturn200() {
//        UUID fixedId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
//        AccountPasswordUpdateDto updateDto = new AccountPasswordUpdateDto("oldpassword", "securepassword");
//        LocalDateTime fixedTime = LocalDateTime.of(2023, 1, 1, 12, 0);
//        ShortAccountResponseDto expectedBody = new ShortAccountResponseDto(fixedId, "email@gmail.com", fixedTime);
//
//        when(principalDetails.getAccountId()).thenReturn(fixedId);
//        when(service.updatePassword(eq(updateDto))).thenReturn(expectedBody);
//
//        ResponseEntity<?> responseEntity = accountController.updatePassword(principalDetails, updateDto);
//        ShortAccountResponseDto responseBody = (ShortAccountResponseDto) responseEntity.getBody();
//
//        verify(service, times(1)).updatePassword(eq(updateDto));
//        assertNotNull(responseBody);
//        assertEquals(fixedId, responseBody.accountId());
//        assertEquals(expectedBody.email(), responseBody.email());
//        assertEquals(expectedBody.createdAt(), responseBody.createdAt());
//    }
//
//    @Test
//    @DisplayName("UT: updatePassword() when dto invalid should return 400")
//    public void updatePassword_whenDtoInvalid_shouldReturn400() {
//        UUID fixedId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
//        AccountPasswordUpdateDto updateDto = new AccountPasswordUpdateDto("oldpassword", "securepassword");
//
//        when(principalDetails.getAccountId()).thenReturn(fixedId);
//        when(service.updatePassword(eq(updateDto))).thenThrow(RuntimeException.class);
//
//        assertThrows(RuntimeException.class, () -> accountController.updatePassword(principalDetails, updateDto));
//        verify(service, times(1)).updatePassword(eq(updateDto));
//    }
//
//    @Test
//    @DisplayName("UT: updatePassword() when accountId from Authenticate is invalid should return 400")
//    public void updatePassword_whenPrincipalIdInvalid_shouldReturn400() {
//        UUID fixedId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
//        AccountPasswordUpdateDto updateDto = new AccountPasswordUpdateDto("oldpassword", "securepassword");
//
//        when(principalDetails.getAccountId()).thenThrow(RuntimeException.class);
//
//        assertThrows(RuntimeException.class, () -> accountController.updatePassword(principalDetails, updateDto));
//        verify(service, times(0)).updatePassword(eq(updateDto));
//    }
}