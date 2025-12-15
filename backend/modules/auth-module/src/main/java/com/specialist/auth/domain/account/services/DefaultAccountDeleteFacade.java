package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.infrastructure.AccountDeleteTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultAccountDeleteFacade implements AccountDeleteFacade {

    private final AccountService accountService;
    private final AccountDeleteTaskService accountDeleteTaskService;

    @Transactional
    @Override
    public void delete(UUID id) {
        String stringRole = accountService.findRoleById(id).toString();
        accountService.softDeleteById(id);
        accountDeleteTaskService.save(id, stringRole);
    }
}
