package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.OAuth2AccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.role.Role;
import com.specialist.contracts.auth.AccountCreateEvent;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountPersistFacadeImpl implements AccountPersistFacade {

    private final AccountService accountService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public ShortAccountResponseDto save(DefaultAccountCreateDto dto, HttpServletResponse response) {
        dto.setRole(Role.ROLE_UNCOMPLETED_USER);
        dto.setAuthorities(List.of());
        ShortAccountResponseDto responseDto = accountService.save(dto);
        eventPublisher.publishEvent(new AccountCreateEvent(responseDto.id(), responseDto.email()));
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
        eventPublisher.publishEvent(new AccountCreateEvent(responseDto.id(), responseDto.email()));
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
        eventPublisher.publishEvent(new AccountCreateEvent(responseDto.id(), responseDto.email()));
        return responseDto;
    }
}
