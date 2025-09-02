package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.oauth2.provider.Provider;
import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.OAuth2AccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityServiceImpl;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.infrastructure.message.services.ConfirmationService;
import com.specialist.contracts.user.ShortUserCreateDto;
import com.specialist.contracts.user.SystemUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// WARNING: all @Transactional should use till @Bean of SystemUserService is in the same app context

@Service
@RequiredArgsConstructor
public class AccountPersistOrchestratorImpl implements AccountPersistOrchestrator {

    private final AccountService accountService;
    private final SystemUserService systemUserService;
    private final ConfirmationService confirmationService;

    @Transactional
    @Override
    public ShortAccountResponseDto save(DefaultAccountCreateDto dto, HttpServletResponse response) {
        dto.setRole(Role.ROLE_USER);
        dto.setAuthorities(AuthorityServiceImpl.DEFAULT_POST_REGISTER_USER_AUTHORITIES);
        ShortAccountResponseDto responseDto = accountService.save(dto);
        systemUserService.save(new ShortUserCreateDto(responseDto.id(), responseDto.email()));
        confirmationService.sendConfirmationCode(dto.getEmail());
        return responseDto;
    }

    @Transactional
    @Override
    public ShortAccountResponseDto save(String email, Provider provider) {
        OAuth2AccountCreateDto dto = new OAuth2AccountCreateDto(
                email,
                provider,
                Role.ROLE_USER,
                AuthorityServiceImpl.DEFAULT_POST_REGISTER_USER_AUTHORITIES
        );
        ShortAccountResponseDto responseDto = accountService.save(dto);
        systemUserService.save(new ShortUserCreateDto(responseDto.id(), responseDto.email()));
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
        systemUserService.save(new ShortUserCreateDto(responseDto.id(), responseDto.email()));
        confirmationService.sendConfirmationCode(dto.email());
        return responseDto;
    }
}
