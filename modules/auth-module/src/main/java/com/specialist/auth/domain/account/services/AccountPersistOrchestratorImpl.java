package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.OAuth2AccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.models.events.AccountCreateEvent;
import com.specialist.auth.domain.role.Role;
import com.specialist.contracts.user.SystemUserPersistService;
import com.specialist.contracts.user.dto.ShortUserCreateDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// WARNING: all @Transactional should use till @Bean of SystemUserService is in the same app context
@Service
@RequiredArgsConstructor
public class AccountPersistOrchestratorImpl implements AccountPersistOrchestrator {

    private final AccountService accountService;
    private final SystemUserPersistService systemUserPersistService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public ShortAccountResponseDto save(DefaultAccountCreateDto dto, HttpServletResponse response) {
        dto.setRole(Role.ROLE_UNCOMPLETED_USER);
        dto.setAuthorities(List.of());
        ShortAccountResponseDto responseDto = accountService.save(dto);
        systemUserPersistService.save(new ShortUserCreateDto(responseDto.id(), responseDto.email()));
        eventPublisher.publishEvent(new AccountCreateEvent(dto.getEmail()));
        return responseDto;
    }

    @Transactional
    @Override
    public ShortAccountResponseDto save(String email, Provider provider) {
        OAuth2AccountCreateDto dto = new OAuth2AccountCreateDto(
                email,
                provider,
                Role.ROLE_UNCOMPLETED_USER,
                List.of()
        );
        ShortAccountResponseDto responseDto = accountService.save(dto);
        systemUserPersistService.save(new ShortUserCreateDto(responseDto.id(), responseDto.email()));
        return responseDto;
    }

    @Transactional
    @Override
    public ShortAccountResponseDto save(ManagedAccountCreateDto dto) {
        DefaultAccountCreateDto createDto = new DefaultAccountCreateDto(
                dto.email(),
                dto.password(),
                dto.role(),
                dto.authorities()
        );
        ShortAccountResponseDto responseDto = accountService.save(createDto);
        systemUserPersistService.save(new ShortUserCreateDto(responseDto.id(), responseDto.email()));
        eventPublisher.publishEvent(new AccountCreateEvent(dto.email()));
        return responseDto;
    }
}
