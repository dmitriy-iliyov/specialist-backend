package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.AccountAuthService;
import com.specialist.auth.infrastructure.message.services.ConfirmationService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SystemAccountFacadeImplUnitTests {

    @Mock
    private AccountService accountService;

    @Mock
    private ConfirmationService confirmationService;

    @Mock
    private AccountAuthService authService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private SystemAccountFacadeImpl service;

    @Test
    @DisplayName("UT: findIdByEmail() should return UUID on success")
    void findIdByEmail_success() {
        String email = "user@example.com";
        UUID expectedId = UUID.randomUUID();

        when(accountService.findIdByEmail(email)).thenReturn(expectedId);

        UUID actual = service.findIdByEmail(email);

        assertEquals(expectedId, actual);

        verify(accountService, times(1)).findIdByEmail(email);
        verifyNoMoreInteractions(accountService);
        verifyNoInteractions(confirmationService);
    }

    @Test
    @DisplayName("UT: findIdByEmail() should throw when accountService throws")
    void findIdByEmail_throwsException() {
        String email = "fail@example.com";

        when(accountService.findIdByEmail(email)).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.findIdByEmail(email));

        verify(accountService, times(1)).findIdByEmail(email);
        verifyNoMoreInteractions(accountService);
        verifyNoInteractions(confirmationService);
    }

    @Test
    @DisplayName("UT: updateEmailById() should update email and send confirmation code")
    void updateEmailById_success() {
        UUID id = UUID.randomUUID();
        String newEmail = "new@example.com";

        doNothing().when(accountService).updateEmailById(id, newEmail);
        doNothing().when(confirmationService).sendConfirmationCode(newEmail);

        service.updateEmailById(id, newEmail);

        verify(accountService, times(1)).updateEmailById(id, newEmail);
        verify(confirmationService, times(1)).sendConfirmationCode(newEmail);

        verifyNoMoreInteractions(accountService, confirmationService);
    }

    @Test
    @DisplayName("UT: updateEmailById() should not send confirmation if accountService.updateEmailById throws")
    void updateEmailById_accountServiceThrows() {
        UUID id = UUID.randomUUID();
        String newEmail = "fail@example.com";

        doThrow(new RuntimeException("Update failed")).when(accountService).updateEmailById(id, newEmail);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateEmailById(id, newEmail));

        verify(accountService, times(1)).updateEmailById(id, newEmail);
        verifyNoMoreInteractions(accountService);

        verifyNoInteractions(confirmationService);
    }

    @Test
    @DisplayName("UT: deleteById() should call accountService.deleteById")
    void deleteById_success() {
        UUID id = UUID.randomUUID();
        UUID refreshTokenId = UUID.randomUUID();

        doNothing().when(accountService).deleteById(id);
        doNothing().when(authService).logout(any(UUID.class), any(HttpServletResponse.class));

        service.deleteById(id, refreshTokenId, response);

        verify(accountService, times(1)).deleteById(id);
        verifyNoMoreInteractions(accountService);
        verifyNoInteractions(confirmationService);
    }

    @Test
    @DisplayName("UT: deleteById() should throw if accountService.deleteById throws")
    void deleteById_throwsException() {
        UUID id = UUID.randomUUID();
        UUID refreshTokenId = UUID.randomUUID();

        doThrow(new RuntimeException("Delete failed")).when(accountService).deleteById(id);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deleteById(id, refreshTokenId, response));

        verify(accountService, times(1)).deleteById(id);
        verifyNoMoreInteractions(accountService);
        verifyNoInteractions(confirmationService);
    }
}