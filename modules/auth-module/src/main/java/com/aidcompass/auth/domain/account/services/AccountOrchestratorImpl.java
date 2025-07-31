package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.AccountCreateDto;
import com.aidcompass.auth.domain.account.models.AccountResponseDto;
import com.aidcompass.message.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountOrchestratorImpl implements AccountOrchestrator {

    private final AccountService accountService;
    private final ConfirmationService confirmationService;


    @Override
    public AccountResponseDto save(AccountCreateDto dto) {
        AccountResponseDto responseDto = accountService.save(dto);
        confirmationService.sendConfirmationMessage(responseDto.id(), dto.getEmail());
        return responseDto;
    }
}
