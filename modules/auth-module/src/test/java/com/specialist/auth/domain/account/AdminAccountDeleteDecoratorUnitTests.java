package com.specialist.auth.domain.account;

import com.specialist.auth.core.TokenManager;
import com.specialist.auth.domain.account.services.AccountDeleteFacade;
import com.specialist.auth.domain.account.services.AdminAccountDeleteDecorator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class AdminAccountDeleteDecoratorUnitTests {

    @Mock
    private AccountDeleteFacade delegate;

    @Mock
    private TokenManager tokenManager;

    @InjectMocks
    private AdminAccountDeleteDecorator decorator;

    @Test
    @DisplayName("UT: delete() should call delegate and revoke tokens")
    void delete_shouldCallDelegateAndRevoke() {
        UUID id = UUID.randomUUID();

        decorator.delete(id);

        verify(delegate).delete(id);
        verify(tokenManager).revokeAll(id);
        verifyNoMoreInteractions(delegate, tokenManager);
    }
}
