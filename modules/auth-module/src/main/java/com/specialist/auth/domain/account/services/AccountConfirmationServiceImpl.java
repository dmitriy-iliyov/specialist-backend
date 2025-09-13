package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountConfirmationServiceImpl implements AccountConfirmationService {

    private final AccountRepository repository;

    @Transactional
    @Override
    public void confirmByEmail(String email) {
        repository.enableByEmail(email);
    }
}
