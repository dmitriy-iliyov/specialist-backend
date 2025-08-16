package com.specialist.auth.domain.account.services;

import com.specialist.auth.infrastructure.message.services.ConfirmationService;
import com.specialist.contracts.auth.SystemAccountFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemAccountFacadeImpl implements SystemAccountFacade {

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
        confirmationService.sendConfirmationCode(email);
    }

    @Override
    public void deleteById(UUID id) {
        accountService.deleteById(id);
    }
}
