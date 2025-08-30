package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityServiceImpl;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.infrastructure.message.services.ConfirmationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersistAccountOrchestratorImpl implements PersistAccountOrchestrator {

    private final AccountService accountService;
    private final ConfirmationService confirmationService;

    @Override
    public ShortAccountResponseDto save(DefaultAccountCreateDto dto, HttpServletResponse response) {
        dto.setRole(Role.ROLE_USER);
        dto.setAuthorities(AuthorityServiceImpl.DEFAULT_POST_REGISTER_USER_AUTHORITIES);
        ShortAccountResponseDto responseDto = accountService.save(dto);
        confirmationService.sendConfirmationCode(dto.getEmail());
        return responseDto;
    }

    @Override
    public ShortAccountResponseDto save(ManagedAccountCreateDto dto) {
        DefaultAccountCreateDto createDto = new DefaultAccountCreateDto(
                dto.email(),
                dto.password(),
                Role.valueOf(dto.role()),
                dto.authorities().stream()
                        .map(Authority::valueOf)
                        .toList()
        );
        ShortAccountResponseDto responseDto = accountService.save(createDto);
        confirmationService.sendConfirmationCode(dto.email());
        return responseDto;
    }
}
