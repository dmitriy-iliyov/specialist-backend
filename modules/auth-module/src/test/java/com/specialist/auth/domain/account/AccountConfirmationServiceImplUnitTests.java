package com.specialist.auth.domain.account;

import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.account.services.AccountConfirmationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AccountConfirmationServiceImplUnitTests {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountConfirmationServiceImpl service;

    @Test
    @DisplayName("UT: confirmByEmail() should call repository enableByEmail")
    void confirmByEmail_shouldCallRepository() {
        String email = "test@example.com";

        service.confirmByEmail(email);

        verify(repository).enableByEmail(email);
        verifyNoMoreInteractions(repository);
    }
}
