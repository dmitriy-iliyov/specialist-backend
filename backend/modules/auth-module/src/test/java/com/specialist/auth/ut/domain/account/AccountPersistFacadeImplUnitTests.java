package com.specialist.auth.ut.domain.account;

import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.OAuth2AccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.services.AccountPersistFacadeImpl;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.role.Role;
import com.specialist.contracts.auth.AccountCreateEvent;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountPersistFacadeImplUnitTests {

    @Mock
    private AccountService accountService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AccountPersistFacadeImpl facade;

    @Test
    @DisplayName("UT: save(DefaultAccountCreateDto) should set role, save and publish event")
    void save_defaultDto_shouldSetRoleAndPublish() {
        DefaultAccountCreateDto dto = new DefaultAccountCreateDto("test@example.com", "pass", null, null);
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(UUID.randomUUID(), "test@example.com");

        when(accountService.save(dto)).thenReturn(responseDto);

        ShortAccountResponseDto result = facade.save(dto, response);

        assertEquals(responseDto, result);
        assertEquals(Role.ROLE_UNCOMPLETED_USER, dto.getRole());
        assertEquals(Collections.emptyList(), dto.getAuthorities());
        verify(accountService).save(dto);
        verify(eventPublisher).publishEvent(any(AccountCreateEvent.class));
        verifyNoMoreInteractions(accountService, eventPublisher);
    }

    @Test
    @DisplayName("UT: save(email, provider) should create oauth dto, save and publish event")
    void save_oauth_shouldCreateDtoAndPublish() {
        String email = "test@example.com";
        Provider provider = Provider.GOOGLE;
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(UUID.randomUUID(), email);

        when(accountService.save(any(OAuth2AccountCreateDto.class))).thenReturn(responseDto);

        ShortAccountResponseDto result = facade.save(email, provider);

        assertEquals(responseDto, result);
        verify(accountService).save(argThat((OAuth2AccountCreateDto dto) -> 
            dto.email().equals(email) && 
            dto.provider() == provider && 
            dto.role() == Role.ROLE_UNCOMPLETED_USER &&
            dto.authorities().isEmpty()
        ));
        verify(eventPublisher).publishEvent(any(AccountCreateEvent.class));
        verifyNoMoreInteractions(accountService, eventPublisher);
    }

    @Test
    @DisplayName("UT: save(ManagedAccountCreateDto) should map to default dto, save and publish event")
    void save_managedDto_shouldMapAndPublish() {
        ManagedAccountCreateDto dto = new ManagedAccountCreateDto("test@example.com", "pass", Role.ROLE_ADMIN, Collections.emptyList());
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(UUID.randomUUID(), "test@example.com");

        when(accountService.save(any(DefaultAccountCreateDto.class))).thenReturn(responseDto);

        ShortAccountResponseDto result = facade.save(dto);

        assertEquals(responseDto, result);
        verify(accountService).save(argThat((DefaultAccountCreateDto d) -> 
            d.getEmail().equals(dto.email()) &&
            d.getPassword().equals(dto.password()) &&
            d.getRole() == dto.role() &&
            d.getAuthorities().equals(dto.authorities())
        ));
        verify(eventPublisher).publishEvent(any(AccountCreateEvent.class));
        verifyNoMoreInteractions(accountService, eventPublisher);
    }
}
