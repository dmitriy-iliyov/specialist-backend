package com.specialist.auth.domain.account;

import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.account.services.AccountUserDetailsService;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.exceptions.AccountNotFoundByEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountUserDetailsServiceUnitTests {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountUserDetailsService service;

    @Test
    @DisplayName("UT: loadUserByUsername() when account exists should return AccountUserDetails")
    void loadUserByUsername_whenAccountExists_shouldReturnUserDetails() {
        String email = "test@example.com";
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(UUID.randomUUID());
        accountEntity.setEmail(email);
        accountEntity.setPassword("password");
        accountEntity.setProvider(Provider.LOCAL);
        
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(Role.ROLE_USER);
        accountEntity.setRole(roleEntity);
        
        accountEntity.setAuthorities(Collections.singletonList(new AuthorityEntity(Authority.ACCOUNT_CREATE)));
        accountEntity.setLocked(false);
        accountEntity.setEnabled(true);

        when(repository.findByEmail(email)).thenReturn(Optional.of(accountEntity));

        UserDetails userDetails = service.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof AccountUserDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(accountEntity.getPassword(), userDetails.getPassword());
        
        // Check authorities: ROLE_USER (from role) + ACCOUNT_CREATE (from authorities list)
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ACCOUNT_CREATE")));

        verify(repository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("UT: loadUserByUsername() when account not found should throw AccountNotFoundByEmailException")
    void loadUserByUsername_whenAccountNotFound_shouldThrowException() {
        String email = "nonexistent@example.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundByEmailException.class, () -> service.loadUserByUsername(email));

        verify(repository, times(1)).findByEmail(email);
    }
}
