package com.specialist.auth.domain.service_account;


import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityService;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.role.RoleService;
import com.specialist.auth.domain.service_account.*;
import com.specialist.auth.domain.service_account.models.ServiceAccountDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountEntity;
import com.specialist.auth.domain.service_account.models.ServiceAccountResponseDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.specialist.auth.exceptions.ServiceAccountNotFoundByIdException;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnifiedServiceAccountServiceUnitTests {

    @Mock
    ServiceAccountRepository repository;

    @Mock
    ServiceAccountMapper mapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleService roleService;

    @Mock
    AuthorityService authorityService;

    @InjectMocks
    UnifiedServiceAccountService service;

    @Test
    @DisplayName("UT: loadUserByUsername() when service account found should return UserDetails")
    void loadUserByUsername_whenFound_shouldReturnUserDetails() {
        UUID id = UUID.randomUUID();
        RoleEntity roleEntity = mock(RoleEntity.class);
        when(roleEntity.getAuthority()).thenReturn("ROLE_ADMIN");

        AuthorityEntity authEntity = mock(AuthorityEntity.class);
        when(authEntity.getAuthority()).thenReturn("AUTH_READ");

        ServiceAccountEntity entity = new ServiceAccountEntity();
        entity.setId(id);
        entity.setSecret("encodedSecret");
        entity.setRole(roleEntity);
        entity.setAuthorities(List.of(authEntity));

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        UserDetails userDetails = service.loadUserByUsername(id.toString());

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof ServiceAccountUserDetails);
        assertEquals(id.toString(), userDetails.getUsername());
        assertEquals("encodedSecret", userDetails.getPassword());

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> ((SimpleGrantedAuthority) a).getAuthority().equals("AUTH_READ")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> ((SimpleGrantedAuthority) a).getAuthority().equals("ROLE_ADMIN")));

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("UT: loadUserByUsername() when service account not found should throw exception")
    void loadUserByUsername_whenNotFound_shouldThrow() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ServiceAccountNotFoundByIdException.class,
                () -> service.loadUserByUsername(id.toString()));

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("UT: loadUserByUsername() when authorities empty should return UserDetails with only role")
    void loadUserByUsername_whenAuthoritiesEmpty_shouldReturnUserDetailsWithRoleOnly() {
        UUID id = UUID.randomUUID();
        RoleEntity roleEntity = mock(RoleEntity.class);
        when(roleEntity.getAuthority()).thenReturn("ROLE_USER");

        ServiceAccountEntity entity = new ServiceAccountEntity();
        entity.setId(id);
        entity.setSecret("secret");
        entity.setRole(roleEntity);
        entity.setAuthorities(Collections.emptyList());

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        UserDetails userDetails = service.loadUserByUsername(id.toString());

        assertNotNull(userDetails);
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> ((SimpleGrantedAuthority) a).getAuthority().equals("ROLE_USER")));

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("UT: save() when dto has id and entity found should update and save")
    void save_whenDtoHasIdAndEntityFound_shouldUpdateAndSave() {
        UUID adminId = UUID.randomUUID();
        UUID dtoId = UUID.randomUUID();
        ServiceAccountDto dto = new ServiceAccountDto();
        dto.setId(dtoId);
        dto.setSecret("secret123");
        dto.setRole(Role.ROLE_USER);
        dto.setAuthorities(Set.of(Authority.REVIEW_CREATE_UPDATE));

        ServiceAccountEntity existingEntity = new ServiceAccountEntity();
        existingEntity.setId(dtoId);

        RoleEntity roleEntity = mock(RoleEntity.class);
        when(roleService.getReferenceByRole(dto.getRole())).thenReturn(roleEntity);

        AuthorityEntity authEntity = mock(AuthorityEntity.class);
        when(authorityService.getReferenceAllByAuthorityIn(anyList())).thenReturn(List.of(authEntity));

        when(repository.findById(dtoId)).thenReturn(Optional.of(existingEntity));
        when(passwordEncoder.encode(dto.getSecret())).thenReturn("encodedSecret");
        when(repository.save(existingEntity)).thenReturn(existingEntity);

        Map<UUID, List<Authority>> map = new HashMap<>();
        map.put(dtoId, List.of(Authority.REVIEW_CREATE_UPDATE));
        when(authorityService.findAllByServiceAccountIdIn(anySet())).thenReturn(map);

        when(mapper.toResponseDto(anyList(), eq(existingEntity))).thenReturn(
                new ServiceAccountResponseDto(dtoId, dto.getRole(), List.of(Authority.REVIEW_CREATE_UPDATE),
                        adminId, adminId, null, null));

        ServiceAccountResponseDto response = service.save(adminId, dto);

        assertNotNull(response);
        assertEquals(dtoId, response.id());
        assertEquals(dto.getRole(), response.role());
        assertEquals(1, response.authorities().size());

        verify(repository).findById(dtoId);
        verify(passwordEncoder).encode(dto.getSecret());
        verify(repository).save(existingEntity);
        verify(roleService).getReferenceByRole(dto.getRole());
        verify(authorityService).getReferenceAllByAuthorityIn(anyList());
        verify(authorityService).findAllByServiceAccountIdIn(anySet());
        verify(mapper).toResponseDto(anyList(), eq(existingEntity));
    }

    @Test
    @DisplayName("UT: save() when dto has no id should create new entity and save")
    void save_whenDtoHasNoId_shouldCreateAndSave() {
        UUID adminId = UUID.randomUUID();
        ServiceAccountDto dto = new ServiceAccountDto();
        dto.setSecret("secret123");
        dto.setRole(Role.ROLE_USER);
        dto.setAuthorities(Set.of(Authority.REVIEW_CREATE_UPDATE));

        RoleEntity roleEntity = mock(RoleEntity.class);
        when(roleService.getReferenceByRole(dto.getRole())).thenReturn(roleEntity);

        AuthorityEntity authEntity = mock(AuthorityEntity.class);
        when(authorityService.getReferenceAllByAuthorityIn(anyList())).thenReturn(List.of(authEntity));

        when(passwordEncoder.encode(dto.getSecret())).thenReturn("encodedSecret");

        ServiceAccountEntity savedEntity = new ServiceAccountEntity();
        savedEntity.setId(UUID.randomUUID());
        when(repository.save(any(ServiceAccountEntity.class))).thenReturn(savedEntity);

        Map<UUID, List<Authority>> map = new HashMap<>();
        map.put(savedEntity.getId(), List.of(Authority.REVIEW_CREATE_UPDATE));
        when(authorityService.findAllByServiceAccountIdIn(anySet())).thenReturn(map);

        when(mapper.toResponseDto(anyList(), any(ServiceAccountEntity.class))).thenReturn(
                new ServiceAccountResponseDto(savedEntity.getId(), dto.getRole(), List.of(Authority.REVIEW_CREATE_UPDATE),
                        adminId, adminId, null, null));

        ServiceAccountResponseDto response = service.save(adminId, dto);

        assertNotNull(response);
        assertEquals(dto.getRole(), response.role());
        assertEquals(1, response.authorities().size());

        verify(roleService).getReferenceByRole(dto.getRole());
        verify(authorityService).getReferenceAllByAuthorityIn(anyList());
        verify(passwordEncoder).encode(dto.getSecret());
        verify(repository).save(any(ServiceAccountEntity.class));
        verify(authorityService).findAllByServiceAccountIdIn(anySet());
        verify(mapper).toResponseDto(anyList(), any(ServiceAccountEntity.class));
    }

    @Test
    @DisplayName("UT: findAll() should return paged response with DTOs")
    void findAll_shouldReturnPagedResponse() {
        ServiceAccountEntity entity1 = new ServiceAccountEntity();
        entity1.setId(UUID.randomUUID());
        ServiceAccountEntity entity2 = new ServiceAccountEntity();
        entity2.setId(UUID.randomUUID());

        List<ServiceAccountEntity> entityList = List.of(entity1, entity2);

        Page<ServiceAccountEntity> page = new PageImpl<>(entityList, org.springframework.data.domain.PageRequest.of(0, 10), entityList.size());

        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Map<UUID, List<Authority>> authMap = new HashMap<>();
        authMap.put(entity1.getId(), List.of(Authority.REVIEW_CREATE_UPDATE));
        authMap.put(entity2.getId(), List.of(Authority.ACCOUNT_CREATE));

        when(authorityService.findAllByServiceAccountIdIn(anySet())).thenReturn(authMap);

        when(mapper.toResponseDto(anyList(), any(ServiceAccountEntity.class))).thenAnswer(invocation -> {
            List<Authority> auths = invocation.getArgument(0);
            ServiceAccountEntity ent = invocation.getArgument(1);
            return new ServiceAccountResponseDto(ent.getId(), Role.ROLE_ADMIN, auths, UUID.randomUUID(), UUID.randomUUID(), null, null);
        });

        PageRequest pageRequest = new PageRequest(0, 10, true);

        PageResponse<ServiceAccountResponseDto> response = service.findAll(pageRequest);

        assertNotNull(response);
        assertEquals(2, response.data().size());
        assertEquals(1, response.totalPages());

        verify(repository).findAll(any(Pageable.class));
        verify(authorityService).findAllByServiceAccountIdIn(anySet());
        verify(mapper, times(2)).toResponseDto(anyList(), any(ServiceAccountEntity.class));
    }

    @Test
    @DisplayName("UT: deleteById() when entity exists should delete successfully")
    void deleteById_whenEntityExists_shouldDelete() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        service.deleteById(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("UT: deleteById() when entity not exists should throw exception")
    void deleteById_whenEntityNotExists_shouldThrow() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ServiceAccountNotFoundByIdException.class, () -> service.deleteById(id));

        verify(repository).existsById(id);
        verify(repository, never()).deleteById(any());
    }
}
