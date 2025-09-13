package com.specialist.auth.domain.account.services;

import com.specialist.contracts.user.UserDeleteOrchestrator;
import com.specialist.contracts.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultAccountDeleteFacade implements AccountDeleteFacade {

    private final AccountService accountService;
    private final UserDeleteOrchestrator userDeleteOrchestrator;

    // WARNING: till in the same app context
    @Transactional
    @Override
    public void delete(UUID id) {
        UserType type = UserType.fromStringRole(accountService.findRoleById(id).toString());
        accountService.deleteById(id);
        // TODO if type == SPECIALIST delete from specialists?
        // TODO schedule delete
        userDeleteOrchestrator.delete(id, type);
    }
}
