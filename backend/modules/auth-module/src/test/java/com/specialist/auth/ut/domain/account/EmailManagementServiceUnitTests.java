package com.specialist.auth.ut.domain.account;

import com.specialist.auth.domain.account.models.dtos.AccountEmailUpdateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.models.events.EmailUpdatedEvent;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.account.services.EmailManagementService;
import com.specialist.auth.exceptions.NonUniqueEmailException;
import com.specialist.contracts.profile.ProfileEmailUpdateService;
import com.specialist.contracts.profile.ProfileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailManagementServiceUnitTests {

    @Mock
    private AccountService accountService;

    @Mock
    private ProfileEmailUpdateService profileEmailUpdateService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private EmailManagementService service;

    @Test
    @DisplayName("UT: update() when email exists and belongs to same user should return dto without update")
    void update_whenEmailExistsAndSameUser_shouldReturnDto() {
        UUID id = UUID.randomUUID();
        String email = "test@example.com";
        AccountEmailUpdateDto dto = new AccountEmailUpdateDto("password", email);
        dto.setId(id);
        dto.setType(ProfileType.SPECIALIST);

        when(accountService.existsByEmail(email)).thenReturn(true);
        when(accountService.findIdByEmail(email)).thenReturn(id);

        ShortAccountResponseDto result = service.update(dto);

        assertEquals(id, result.id());
        assertEquals(email, result.email());
        verify(accountService).existsByEmail(email);
        verify(accountService).findIdByEmail(email);
        verify(accountService, never()).updateEmail(any());
        verify(profileEmailUpdateService, never()).updateById(any(), any(), any());
        verifyNoMoreInteractions(accountService, profileEmailUpdateService, eventPublisher);
    }

    @Test
    @DisplayName("UT: update() when email exists and belongs to other user should throw exception")
    void update_whenEmailExistsAndOtherUser_shouldThrowException() {
        UUID id = UUID.randomUUID();
        UUID otherId = UUID.randomUUID();
        String email = "test@example.com";
        AccountEmailUpdateDto dto = new AccountEmailUpdateDto("password", email);
        dto.setId(id);
        dto.setType(ProfileType.SPECIALIST);

        when(accountService.existsByEmail(email)).thenReturn(true);
        when(accountService.findIdByEmail(email)).thenReturn(otherId);

        assertThrows(NonUniqueEmailException.class, () -> service.update(dto));

        verify(accountService).existsByEmail(email);
        verify(accountService).findIdByEmail(email);
        verify(accountService, never()).updateEmail(any());
        verifyNoMoreInteractions(accountService, profileEmailUpdateService, eventPublisher);
    }

    @Test
    @DisplayName("UT: update() when email is new should update account and profile and publish event")
    void update_whenEmailNew_shouldUpdateAndPublish() {
        UUID id = UUID.randomUUID();
        String email = "new@example.com";
        AccountEmailUpdateDto dto = new AccountEmailUpdateDto("password", email);
        dto.setId(id);
        dto.setType(ProfileType.SPECIALIST);
        
        ShortAccountResponseDto expectedResponse = new ShortAccountResponseDto(id, email);

        when(accountService.existsByEmail(email)).thenReturn(false);
        when(accountService.updateEmail(dto)).thenReturn(expectedResponse);

        ShortAccountResponseDto result = service.update(dto);

        assertEquals(expectedResponse, result);
        verify(accountService).existsByEmail(email);
        verify(accountService).updateEmail(dto);
        verify(profileEmailUpdateService).updateById(ProfileType.SPECIALIST, id, email);
        verify(eventPublisher).publishEvent(any(EmailUpdatedEvent.class));
        verifyNoMoreInteractions(accountService, profileEmailUpdateService, eventPublisher);
    }
}
