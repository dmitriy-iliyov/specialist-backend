package com.aidcompass.auth.domain.account.services;

import com.aidcompass.contracts.auth.SystemAccountService;
import com.aidcompass.auth.infrastructure.message.services.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemAccountServiceImpl implements SystemAccountService {

    private final AccountService accountService;
    private final ConfirmationService confirmationService;


    @Override
    public UUID findIdByEmail(String email) {
        return accountService.findIdByEmail(email);
    }

    @Transactional
    @Override
    public void updateEmailById(UUID id, String email) {
        accountService.updateEmailById(id, email);
        confirmationService.sendConfirmationMessage(id, email);
    }

    @Override
    public void deleteById(UUID id) {
        accountService.deleteById(id);
    }
}
