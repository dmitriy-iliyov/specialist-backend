package com.specialist.auth.ut.domain.service_account;


import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityService;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.role.RoleService;
import com.specialist.auth.domain.service_account.SecretGenerationStrategy;
import com.specialist.auth.domain.service_account.ServiceAccountMapper;
import com.specialist.auth.domain.service_account.ServiceAccountRepository;
import com.specialist.auth.domain.service_account.UnifiedServiceAccountService;
import com.specialist.auth.domain.service_account.models.SecretServiceAccountResponseDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountDto;
import com.specialist.auth.domain.service_account.models.ServiceAccountEntity;
import com.specialist.auth.domain.service_account.models.ServiceAccountResponseDto;
import com.specialist.auth.exceptions.ServiceAccountNotFoundByIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
    SecretGenerationStrategy secretGenerationStrategy;

    @Mock
    RoleService roleService;

    @Mock
    AuthorityService authorityService;

    @InjectMocks
    UnifiedServiceAccountService service;

    @Test
    @DisplayName("UT: save() when dto has accountId and entity found should update and save")
    void save_whenDtoHasIdAndEntityFound_shouldUpdatePasswordAndSave() {
        UUID adminId = UUID.randomUUID();
        UUID dtoId = UUID.randomUUID();
        ServiceAccountDto dto = new ServiceAccountDto();
        dto.setId(dtoId);
        dto.setRole(Role.ROLE_USER);
        dto.setAuthorities(Set.of(Authority.REVIEW_CREATE_UPDATE));

        ServiceAccountEntity existingEntity = new ServiceAccountEntity();
        existingEntity.setId(dtoId);

        RoleEntity roleEntity = mock(RoleEntity.class);
        when(roleService.getReferenceByRole(dto.getRole())).thenReturn(roleEntity);

        when(repository.findById(dtoId)).thenReturn(Optional.of(existingEntity));when(repository.save(existingEntity)).thenReturn(existingEntity);
        when(secretGenerationStrategy.generate()).thenReturn("random-secret-string");

        when(mapper.toResponseDto(anyList(), eq(existingEntity))).thenReturn(
                new ServiceAccountResponseDto(dtoId, "account-name", dto.getRole(), List.of(Authority.REVIEW_CREATE_UPDATE),
                        adminId, adminId, null, null));

        SecretServiceAccountResponseDto response = service.save(adminId, dto);

        assertNotNull(response);
        assertEquals(dtoId, response.dto().id());
        assertEquals(dto.getRole(), response.dto().role());
        assertEquals(1, response.dto().authorities().size());

        verify(repository).findById(dtoId);
        verify(repository).save(existingEntity);
        verify(roleService).getReferenceByRole(dto.getRole());
        verify(authorityService).getReferenceAllByAuthorityIn(anyList());
        verify(secretGenerationStrategy, times(1)).generate();
        verify(mapper).toResponseDto(anyList(), eq(existingEntity));
    }

    @Test
    @DisplayName("UT: save() when dto has no accountId should create new entity and save")
    void save_whenDtoHasNoId_shouldCreateAndSave() {
        UUID adminId = UUID.randomUUID();
        ServiceAccountDto dto = new ServiceAccountDto();
        dto.setRole(Role.ROLE_USER);
        dto.setAuthorities(Set.of(Authority.REVIEW_CREATE_UPDATE));

        RoleEntity roleEntity = mock(RoleEntity.class);
        when(roleService.getReferenceByRole(dto.getRole())).thenReturn(roleEntity);

        AuthorityEntity authEntity = mock(AuthorityEntity.class);
        when(authorityService.getReferenceAllByAuthorityIn(anyList())).thenReturn(List.of(authEntity));

        ServiceAccountEntity savedEntity = new ServiceAccountEntity();
        savedEntity.setId(UUID.randomUUID());
        savedEntity.setAuthorities(List.of(new AuthorityEntity(Authority.REVIEW_CREATE_UPDATE)));
        when(repository.save(any(ServiceAccountEntity.class))).thenReturn(savedEntity);

        when(secretGenerationStrategy.generate()).thenReturn("random-secret-string");

        when(mapper.toResponseDto(anyList(), any(ServiceAccountEntity.class))).thenReturn(
                new ServiceAccountResponseDto(savedEntity.getId(), "account-name", dto.getRole(), List.of(Authority.REVIEW_CREATE_UPDATE),
                        adminId, adminId, null, null));

        SecretServiceAccountResponseDto response = service.save(adminId, dto);

        assertNotNull(response);
        assertEquals(dto.getRole(), response.dto().role());
        assertEquals(1, response.dto().authorities().size());

        verify(roleService).getReferenceByRole(dto.getRole());
        verify(authorityService).getReferenceAllByAuthorityIn(anyList());
        verify(repository).save(any(ServiceAccountEntity.class));
        verify(secretGenerationStrategy, times(1)).generate();
        verify(mapper).toResponseDto(anyList(), any(ServiceAccountEntity.class));
    }

    @Test
    @DisplayName("UT: deleteById() when entity exists should delete successfully")
    void deleteById_whenEntityExists_shouldHardDelete() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        service.deleteById(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("UT: deleteById() when entity not exists should throw exception")
    void hardDeleteById_whenEntityNotExists_shouldThrow() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ServiceAccountNotFoundByIdException.class, () -> service.deleteById(id));

        verify(repository).existsById(id);
        verify(repository, never()).deleteById(any());
    }
}
