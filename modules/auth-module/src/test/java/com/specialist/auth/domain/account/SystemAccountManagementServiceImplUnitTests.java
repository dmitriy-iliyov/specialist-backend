package com.specialist.auth.ut.domain.account;

import com.specialist.auth.domain.account.mappers.AccountMapper;
import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.account.services.SystemAccountManagementServiceImpl;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.exceptions.AccountNotFoundByIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SystemAccountManagementServiceImplUnitTests {

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper mapper;

    @InjectMocks
    private SystemAccountManagementServiceImpl service;

    @Test
    @DisplayName("UT: takeAwayAuthoritiesById() when account exists should remove authorities and return dto")
    void takeAwayAuthoritiesById_whenAccountExists_shouldRemoveAuthoritiesAndReturnDto() {
        UUID id = UUID.randomUUID();
        Set<Authority> authoritiesToRemove = Set.of(Authority.ACCOUNT_CREATE);
        
        AuthorityEntity createAuth = new AuthorityEntity(Authority.ACCOUNT_CREATE);
        List<AuthorityEntity> currentAuthorities = new ArrayList<>(List.of(createAuth));
        
        AccountEntity accountEntity = mock(AccountEntity.class);
        when(accountEntity.getAuthorities()).thenReturn(currentAuthorities);
        
        ShortAccountResponseDto expectedDto = mock(ShortAccountResponseDto.class);

        when(repository.findById(id)).thenReturn(Optional.of(accountEntity));
        when(repository.save(accountEntity)).thenReturn(accountEntity);
        when(mapper.toShortResponseDto(accountEntity)).thenReturn(expectedDto);

        ShortAccountResponseDto result = service.takeAwayAuthoritiesById(id, authoritiesToRemove);

        assertEquals(expectedDto, result);
        verify(repository).findById(id);
        verify(repository).save(accountEntity);
        verify(mapper).toShortResponseDto(accountEntity);
        verifyNoMoreInteractions(repository, mapper);
        
        assert(currentAuthorities.isEmpty());
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
        verifyNoMoreInteractions(repository, mapper);
    }
}
