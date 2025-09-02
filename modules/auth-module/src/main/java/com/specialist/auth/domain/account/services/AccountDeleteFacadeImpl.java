package com.specialist.auth.domain.account.services;

import com.specialist.contracts.user.UserDeleteOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountDeleteOrchestratorImpl implements AccountDeleteOrchestrator {

    private final AccountService accountService;
    private final UserDeleteOrchestrator userDeleteOrchestrator;

    // WARNING: till in the same app context
    @Transactional
    @Override
    public void delete(UUID id) {
        accountService.deleteById(id);
        // TODO schedule delete
        userDeleteOrchestrator.delete(id);
    }
}
