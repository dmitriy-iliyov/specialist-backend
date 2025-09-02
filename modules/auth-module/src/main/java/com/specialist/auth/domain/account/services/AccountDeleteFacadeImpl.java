package com.specialist.auth.domain.account.services;

import com.specialist.contracts.user.UserDeleteFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountDeleteFacadeImpl implements AccountDeleteFacade {

    private final AccountService accountService;
    private final UserDeleteFacade userDeleteFacade;

    // WARNING: till in the same app context
    @Transactional
    @Override
    public void delete(UUID id) {
        accountService.deleteById(id);
        // TODO schedule delete
        userDeleteFacade.delete(id);
    }
}
