package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.exceptions.AccountNotFoundByEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountPasswordRecoveryServiceImpl implements AccountPasswordRecoveryService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository repository;

    @Transactional
    @Override
    public void recoverByEmail(String email, String password) {
        AccountEntity entity = repository.findByEmail(email).orElseThrow(AccountNotFoundByEmailException::new);
        entity.setPassword(passwordEncoder.encode(password));
    }
}
