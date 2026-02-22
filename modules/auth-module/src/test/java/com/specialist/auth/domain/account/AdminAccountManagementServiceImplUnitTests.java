package com.specialist.auth.domain.account;

import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.dtos.DisableRequest;
import com.specialist.auth.domain.account.models.dtos.LockRequest;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.account.services.AdminAccountManagementServiceImpl;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.exceptions.AccountNotFoundByIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminAccountManagementServiceImplUnitTests {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AdminAccountManagementServiceImpl service;

    @Test
    @DisplayName("UT: takeAwayAuthoritiesById() when account exists should remove authorities")
    void takeAwayAuthoritiesById_whenAccountExists_shouldRemoveAuthorities() {
        UUID id = UUID.randomUUID();
        Set<Authority> authoritiesToRemove = Set.of(Authority.ACCOUNT_CREATE);
        
        AuthorityEntity createAuth = new AuthorityEntity(Authority.ACCOUNT_CREATE);
        AuthorityEntity deleteAuth = new AuthorityEntity(Authority.ACCOUNT_DELETE);
        List<AuthorityEntity> currentAuthorities = new ArrayList<>(List.of(createAuth, deleteAuth));
        
        AccountEntity accountEntity = mock(AccountEntity.class);
        when(accountEntity.getAuthorities()).thenReturn(currentAuthorities);
        
        when(repository.findById(id)).thenReturn(Optional.of(accountEntity));

        service.takeAwayAuthoritiesById(id, authoritiesToRemove);

        verify(repository).findById(id);
        verify(repository).save(accountEntity);
        verifyNoMoreInteractions(repository);
        
        assert(currentAuthorities.size() == 1);
        assert(currentAuthorities.get(0).getAuthorityAsEnum().equals(Authority.ACCOUNT_DELETE));
    }

    @Test
    @DisplayName("UT: takeAwayAuthoritiesById() when account not found should throw exception")
    void takeAwayAuthoritiesById_whenAccountNotFound_shouldThrowException() {
        UUID id = UUID.randomUUID();
        Set<Authority> authorities = Set.of(Authority.ACCOUNT_CREATE);
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundByIdException.class, () -> service.takeAwayAuthoritiesById(id, authorities));

        verify(repository).findById(id);
        verify(repository, never()).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("UT: lockById() should call repository")
    void lockById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        LocalDateTime term = LocalDateTime.now();
        LockRequest request = new LockRequest(LockReason.ABUSE, term);
        Instant expectedInstant = term.atZone(ZoneId.systemDefault()).toInstant();

        service.lockById(id, request);

        verify(repository).lockById(id, LockReason.ABUSE, expectedInstant);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("UT: unlockById() should call repository")
    void unlockById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        service.unlockById(id);
        verify(repository).unlockById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("UT: disableById() should call repository")
    void disableById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        DisableRequest request = new DisableRequest(DisableReason.ATTACK_ATTEMPT_DETECTED);
        service.disableById(id, request);
        verify(repository).disableById(id, DisableReason.ATTACK_ATTEMPT_DETECTED);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("UT: enableById() should call repository")
    void enableById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        service.enableById(id);
        verify(repository).enableById(id);
        verifyNoMoreInteractions(repository);
    }
}
