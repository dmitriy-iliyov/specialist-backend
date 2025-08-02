package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.dtos.DefaultAccountCreateDto;
import com.aidcompass.auth.domain.account.models.dtos.ManagedAccountCreateDto;
import com.aidcompass.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.role.Role;
import com.aidcompass.message.services.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountOrchestratorImpl implements AccountOrchestrator {

    private final AccountService accountService;
    private final ConfirmationService confirmationService;
    private static final List<Authority> DEFAULT_USER_AUTHORITIES = new ArrayList<>(List.of(
            Authority.COMMENT_PERMISSION
    ));

    @Override
    public ShortAccountResponseDto save(DefaultAccountCreateDto dto) {
        dto.setRole(Role.ROLE_USER);
        dto.setAuthorities(DEFAULT_USER_AUTHORITIES);
        ShortAccountResponseDto responseDto = accountService.save(dto);
        confirmationService.sendConfirmationMessage(responseDto.id(), dto.getEmail());
        return responseDto;
    }

    @Override
    public ShortAccountResponseDto save(ManagedAccountCreateDto dto) {
        DefaultAccountCreateDto createDto = DefaultAccountCreateDto.builder()
                .email(dto.email())
                .password(dto.password())
                .role(Role.valueOf(dto.role()))
                .authorities(dto.authorities().stream().map(Authority::valueOf).toList())
                .build();
        ShortAccountResponseDto responseDto = accountService.save(createDto);
        confirmationService.sendConfirmationMessage(responseDto.id(), dto.email());
        return responseDto;
    }
}
