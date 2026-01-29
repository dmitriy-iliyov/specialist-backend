package com.specialist.auth.ut.domain.account;

import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.account.services.AccountPasswordRecoveryServiceImpl;
import com.specialist.auth.exceptions.AccountNotFoundByEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountPasswordRecoveryServiceImplUnitTests {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountPasswordRecoveryServiceImpl service;

    @Test
    @DisplayName("UT: recoverByEmail() when account exists should update password")
    void recoverByEmail_whenAccountExists_shouldUpdatePassword() {
        String email = "test@example.com";
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";
        AccountEntity accountEntity = mock(AccountEntity.class);

        when(repository.findByEmail(email)).thenReturn(Optional.of(accountEntity));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        service.recoverByEmail(email, newPassword);

        verify(repository).findByEmail(email);
        verify(passwordEncoder).encode(newPassword);
        verify(accountEntity).setPassword(encodedPassword);
        verifyNoMoreInteractions(repository, passwordEncoder, accountEntity);
    }

    @Test
    @DisplayName("UT: recoverByEmail() when account not found should throw exception")
    void recoverByEmail_whenAccountNotFound_shouldThrowException() {
        String email = "nonexistent@example.com";
        String newPassword = "newPassword";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundByEmailException.class, () -> service.recoverByEmail(email, newPassword));

        verify(repository).findByEmail(email);
        verifyNoInteractions(passwordEncoder);
        verifyNoMoreInteractions(repository);
    }
}
