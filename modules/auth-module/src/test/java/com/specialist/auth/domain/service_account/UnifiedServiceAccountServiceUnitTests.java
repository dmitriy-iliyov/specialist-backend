package com.specialist.auth.domain.service_account;

import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityService;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.role.RoleService;
import com.specialist.auth.domain.service_account.models.SecretServiceAccountResponseDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountEntity;
import com.specialist.auth.domain.service_account.models.ServiceAccountResponseDto;
import com.specialist.auth.exceptions.ServiceAccountNotFoundByIdException;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnifiedServiceAccountServiceUnitTests {

    @Mock
    private ServiceAccountRepository repository;
    @Mock
    private ServiceAccountMapper mapper;
    @Mock
    private SecretGenerationStrategy secretGenerationStrategy;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;
    @Mock
    private AuthorityService authorityService;

    @InjectMocks
    private UnifiedServiceAccountService service;

    @Test
    @DisplayName("UT: save() when creating new account should generate secret and save")
    void save_newAccount_shouldGenerateSecretAndSave() {
        UUID adminId = UUID.randomUUID();
        ServiceAccountDto dto = new ServiceAccountDto();
        dto.setRole(Role.ROLE_SERVICE);
        dto.setAuthorities(Set.of(Authority.SERVICE_ACCOUNT_MANAGER));
        
        String generatedSecret = "secret";
        String encodedSecret = "encodedSecret";
        
        RoleEntity roleEntity = new RoleEntity();
        AuthorityEntity authorityEntity = new AuthorityEntity(Authority.SERVICE_ACCOUNT_MANAGER);
        ServiceAccountEntity savedEntity = new ServiceAccountEntity();
        savedEntity.setAuthorities(Collections.singletonList(authorityEntity));
        ServiceAccountResponseDto responseDto = mock(ServiceAccountResponseDto.class);

        when(roleService.getReferenceByRole(dto.getRole())).thenReturn(roleEntity);
        when(authorityService.getReferenceAllByAuthorityIn(anyList())).thenReturn(Collections.singletonList(authorityEntity));
        when(secretGenerationStrategy.generate()).thenReturn(generatedSecret);
        when(passwordEncoder.encode(generatedSecret)).thenReturn(encodedSecret);
        when(repository.save(any(ServiceAccountEntity.class))).thenReturn(savedEntity);
        when(mapper.toResponseDto(anyList(), eq(savedEntity))).thenReturn(responseDto);

        SecretServiceAccountResponseDto result = service.save(adminId, dto);

        assertEquals(generatedSecret, result.secret());
        assertEquals(responseDto, result.dto());
        verify(roleService).getReferenceByRole(dto.getRole());
        verify(authorityService).getReferenceAllByAuthorityIn(anyList());
        verify(secretGenerationStrategy).generate();
        verify(passwordEncoder).encode(generatedSecret);
        verify(repository).save(any(ServiceAccountEntity.class));
        verify(mapper).toResponseDto(anyList(), eq(savedEntity));
        verifyNoMoreInteractions(roleService, authorityService, secretGenerationStrategy, passwordEncoder, repository, mapper);
    }

    @Test
    @DisplayName("UT: save() when updating existing account should update and regenerate secret")
    void save_existingAccount_shouldUpdateAndRegenerateSecret() {
        UUID adminId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        ServiceAccountDto dto = new ServiceAccountDto();
        dto.setId(accountId);
        dto.setRole(Role.ROLE_SERVICE);
        dto.setAuthorities(Set.of(Authority.SERVICE_ACCOUNT_MANAGER));
        
        ServiceAccountEntity existingEntity = new ServiceAccountEntity();
        
        when(repository.findById(accountId)).thenReturn(Optional.of(existingEntity));
        when(roleService.getReferenceByRole(dto.getRole())).thenReturn(new RoleEntity());
        when(authorityService.getReferenceAllByAuthorityIn(anyList())).thenReturn(Collections.emptyList());
        when(secretGenerationStrategy.generate()).thenReturn("newSecret");
        when(passwordEncoder.encode("newSecret")).thenReturn("encodedNewSecret");
        when(repository.save(existingEntity)).thenReturn(existingEntity);
        when(mapper.toResponseDto(anyList(), eq(existingEntity))).thenReturn(mock(ServiceAccountResponseDto.class));

        service.save(adminId, dto);

        assertEquals("encodedNewSecret", existingEntity.getSecret());
        assertEquals(adminId, existingEntity.getUpdaterId());
        verify(repository).findById(accountId);
        verify(roleService).getReferenceByRole(dto.getRole());
        verify(authorityService).getReferenceAllByAuthorityIn(anyList());
        verify(secretGenerationStrategy).generate();
        verify(passwordEncoder).encode("newSecret");
        verify(repository).save(existingEntity);
        verify(mapper).toResponseDto(anyList(), eq(existingEntity));
        verifyNoMoreInteractions(repository, roleService, authorityService, secretGenerationStrategy, passwordEncoder, mapper);
    }

    @Test
    @DisplayName("UT: findAll() should return page response with mapped dtos")
    void findAll_shouldReturnPageResponse() {
        PageRequest pageRequest = new PageRequest(0, 10, true);
        ServiceAccountEntity entity = new ServiceAccountEntity();
        entity.setId(UUID.randomUUID());
        Page<ServiceAccountEntity> page = new PageImpl<>(Collections.singletonList(entity));
        
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(authorityService.findAllByAccountIdIn(anySet())).thenReturn(Collections.emptyMap());
        when(mapper.toResponseDto(any(), eq(entity))).thenReturn(mock(ServiceAccountResponseDto.class));

        PageResponse<ServiceAccountResponseDto> result = service.findAll(pageRequest);

        assertEquals(1, result.data().size());
        assertEquals(1, result.totalPages());
        verify(repository).findAll(any(Pageable.class));
        verify(authorityService).findAllByAccountIdIn(anySet());
        verify(mapper).toResponseDto(any(), eq(entity));
        verifyNoMoreInteractions(repository, authorityService, mapper);
    }

    @Test
    @DisplayName("UT: deleteById() when exists should delete")
    void deleteById_whenExists_shouldDelete() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);
        service.deleteById(id);
        verify(repository).existsById(id);
        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("UT: deleteById() when not exists should throw exception")
    void deleteById_whenNotExists_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);
        assertThrows(ServiceAccountNotFoundByIdException.class, () -> service.deleteById(id));
        verify(repository).existsById(id);
        verify(repository, never()).deleteById(any());
        verifyNoMoreInteractions(repository);
    }
}
