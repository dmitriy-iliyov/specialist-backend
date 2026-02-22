package com.specialist.auth.domain.role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplUnitTests {

    @Mock
    private RoleRepository repository;

    @Mock
    private RoleCacheService cacheService;

    @InjectMocks
    private RoleServiceImpl service;

    @Test
    @DisplayName("UT: getReferenceByRole() when cache hit should return reference")
    void getReferenceByRole_cacheHit_shouldReturnReference() {
        Role role = Role.ROLE_USER;
        RoleEntity entity = new RoleEntity();
        
        when(cacheService.getRoleId(role)).thenReturn(1L);
        when(repository.getReferenceById(1L)).thenReturn(entity);

        RoleEntity result = service.getReferenceByRole(role);

        assertEquals(entity, result);
        verify(cacheService).getRoleId(role);
        verify(repository).getReferenceById(1L);
        verify(repository, never()).getReferenceByRole(any());
        verifyNoMoreInteractions(cacheService, repository);
    }

    @Test
    @DisplayName("UT: getReferenceByRole() when cache miss should call repository")
    void getReferenceByRole_cacheMiss_shouldCallRepository() {
        Role role = Role.ROLE_USER;
        when(cacheService.getRoleId(role)).thenReturn(null);
        
        service.getReferenceByRole(role);

        verify(cacheService).getRoleId(role);
        verify(repository).getReferenceByRole(role);
        verifyNoMoreInteractions(cacheService, repository);
    }
}
