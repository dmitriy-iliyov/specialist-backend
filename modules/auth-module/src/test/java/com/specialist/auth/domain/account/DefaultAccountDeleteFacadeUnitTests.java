package com.specialist.auth.domain.account;

import com.specialist.auth.domain.account.infrastructure.AccountDeleteTaskService;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.account.services.DefaultAccountDeleteFacade;
import com.specialist.auth.domain.role.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultAccountDeleteFacadeUnitTests {

    @Mock
    private AccountService accountService;

    @Mock
    private AccountDeleteTaskService accountDeleteTaskService;

    @InjectMocks
    private DefaultAccountDeleteFacade facade;

    @Test
    @DisplayName("UT: delete() should find role, soft delete account and save delete task")
    void delete_shouldPerformDeleteSequence() {
        UUID id = UUID.randomUUID();
        Role role = Role.ROLE_USER;

        when(accountService.findRoleById(id)).thenReturn(role);

        facade.delete(id);

        verify(accountService, times(1)).findRoleById(id);
        verify(accountService, times(1)).softDeleteById(id);
        verify(accountDeleteTaskService, times(1)).save(id, role.toString());
    }
}
