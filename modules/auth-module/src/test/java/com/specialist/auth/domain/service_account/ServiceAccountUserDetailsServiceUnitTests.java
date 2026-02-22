package com.specialist.auth.domain.service_account;

import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.service_account.models.ServiceAccountEntity;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.specialist.auth.exceptions.ServiceAccountNotFoundByIdException;
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
class ServiceAccountUserDetailsServiceUnitTests {

    @Mock
    private ServiceAccountRepository repository;

    @InjectMocks
    private ServiceAccountUserDetailsService service;

    @Test
    @DisplayName("UT: loadUserByUsername() when account exists should return ServiceAccountUserDetails")
    void loadUserByUsername_whenAccountExists_shouldReturnUserDetails() {
        UUID id = UUID.randomUUID();
        ServiceAccountEntity entity = new ServiceAccountEntity();
        entity.setId(id);
        entity.setSecret("secret");
        
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(Role.ROLE_SERVICE);
        entity.setRole(roleEntity);
        
        entity.setAuthorities(Collections.singletonList(new AuthorityEntity(Authority.SERVICE_ACCOUNT_MANAGER)));

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        UserDetails userDetails = service.loadUserByUsername(id.toString());

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof ServiceAccountUserDetails);
        assertEquals(id.toString(), userDetails.getUsername());
        assertEquals("secret", userDetails.getPassword());
        
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SERVICE")));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("SERVICE_ACCOUNT_MANAGER")));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("UT: loadUserByUsername() when account not found should throw exception")
    void loadUserByUsername_whenAccountNotFound_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServiceAccountNotFoundByIdException.class, () -> service.loadUserByUsername(id.toString()));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }
}
