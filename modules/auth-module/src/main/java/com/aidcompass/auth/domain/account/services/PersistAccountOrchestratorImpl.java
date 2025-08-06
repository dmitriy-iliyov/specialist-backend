package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.aidcompass.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.aidcompass.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.role.Role;
import com.aidcompass.auth.infrastructure.message.services.ConfirmationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersistAccountOrchestratorImpl implements PersistAccountOrchestrator {

    private final AccountService accountService;
    private final ConfirmationService confirmationService;
    private static final List<Authority> DEFAULT_USER_AUTHORITIES = new ArrayList<>(List.of(
            Authority.SPECIALIST_CREATE_UPDATE, Authority.REVIEW_CREATE_UPDATE, Authority.TYPE_SUGGEST
    ));

    @Override
    public ShortAccountResponseDto save(DefaultAccountCreateDto dto, HttpServletResponse response) {
        dto.setRole(Role.ROLE_USER);
        dto.setAuthorities(DEFAULT_USER_AUTHORITIES);
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
