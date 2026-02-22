package com.specialist.auth.domain.account;

import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.domain.account.mappers.AccountMapper;
import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.dtos.*;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.account.services.AccountCacheService;
import com.specialist.auth.domain.account.services.UnifiedAccountService;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityService;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.role.RoleService;
import com.specialist.auth.exceptions.InvalidPasswordException;
import com.specialist.contracts.profile.ProfileType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnifiedAccountServiceUnitTests {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccountRepository repository;
    @Mock
    private AccountMapper mapper;
    @Mock
    private AccountCacheService cacheService;
    @Mock
    private RoleService roleService;
    @Mock
    private AuthorityService authorityService;

    @InjectMocks
    private UnifiedAccountService service;

    @Test
    @DisplayName("UT: save(DefaultAccountCreateDto) should encode password, save entity and cache email")
    void save_defaultDto_shouldSaveAndCache() {
        DefaultAccountCreateDto dto = new DefaultAccountCreateDto("test@example.com", "password", Role.ROLE_USER, Collections.emptyList());
        RoleEntity roleEntity = new RoleEntity();
        List<AuthorityEntity> authorityEntities = Collections.emptyList();
        AccountEntity savedEntity = new AccountEntity();
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(UUID.randomUUID(), dto.getEmail());

        when(roleService.getReferenceByRole(dto.getRole())).thenReturn(roleEntity);
        when(authorityService.getReferenceAllByAuthorityIn(dto.getAuthorities())).thenReturn(authorityEntities);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(repository.save(any(AccountEntity.class))).thenReturn(savedEntity);
        when(mapper.toShortResponseDto(savedEntity)).thenReturn(responseDto);

        ShortAccountResponseDto result = service.save(dto);

        assertEquals(responseDto, result);
        verify(passwordEncoder).encode(dto.getPassword());
        verify(repository).save(any(AccountEntity.class));
        verify(cacheService).putEmailExistAs(dto.getEmail(), true);
    }

    @Test
    @DisplayName("UT: save(OAuth2AccountCreateDto) should save entity and cache email")
    void save_oauth2Dto_shouldSaveAndCache() {
        OAuth2AccountCreateDto dto = new OAuth2AccountCreateDto("test@example.com", Provider.GOOGLE, Role.ROLE_USER, Collections.emptyList());
        RoleEntity roleEntity = new RoleEntity();
        List<AuthorityEntity> authorityEntities = Collections.emptyList();
        AccountEntity savedEntity = new AccountEntity();
        savedEntity.setEmail(dto.email());
        ShortAccountResponseDto responseDto = new ShortAccountResponseDto(UUID.randomUUID(), dto.email());

        when(roleService.getReferenceByRole(dto.role())).thenReturn(roleEntity);
        when(authorityService.getReferenceAllByAuthorityIn(dto.authorities())).thenReturn(authorityEntities);
        when(repository.save(any(AccountEntity.class))).thenReturn(savedEntity);
        when(mapper.toShortResponseDto(savedEntity)).thenReturn(responseDto);

        ShortAccountResponseDto result = service.save(dto);

        assertEquals(responseDto, result);
        verify(repository).save(any(AccountEntity.class));
        verify(cacheService).putEmailExistAs(dto.email(), true);
    }

    @Test
    @DisplayName("UT: updatePassword() when old password matches should update password")
    void updatePassword_whenOldPasswordMatches_shouldUpdate() {
        UUID id = UUID.randomUUID();
        String oldPassword = "old";
        String newPassword = "new";
        AccountPasswordUpdateDto dto = new AccountPasswordUpdateDto(oldPassword, newPassword);
        dto.setId(id);
        
        AccountEntity entity = new AccountEntity();
        entity.setPassword("encodedOld");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches(oldPassword, "encodedOld")).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNew");
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toShortResponseDto(entity)).thenReturn(new ShortAccountResponseDto(id, "email"));

        service.updatePassword(dto);

        assertEquals("encodedNew", entity.getPassword());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: updatePassword() when old password does not match should throw exception")
    void updatePassword_whenOldPasswordMismatch_shouldThrow() {
        UUID id = UUID.randomUUID();
        AccountPasswordUpdateDto dto = new AccountPasswordUpdateDto("wrong", "new");
        dto.setId(id);
        
        AccountEntity entity = new AccountEntity();
        entity.setPassword("encodedOld");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches("wrong", "encodedOld")).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> service.updatePassword(dto));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("UT: updateEmail() when password matches should update email and cache")
    void updateEmail_whenPasswordMatches_shouldUpdateAndCache() {
        UUID id = UUID.randomUUID();
        String oldEmail = "old@example.com";
        String newEmail = "new@example.com";
        String password = "password";
        
        AccountEmailUpdateDto dto = new AccountEmailUpdateDto(password, newEmail);
        dto.setId(id);
        dto.setType(ProfileType.SPECIALIST);
        
        AccountEntity entity = new AccountEntity();
        entity.setId(id);
        entity.setEmail(oldEmail);
        entity.setPassword("encodedPass");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches("password", "encodedPass")).thenReturn(true);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toShortResponseDto(entity)).thenReturn(new ShortAccountResponseDto(id, newEmail));

        service.updateEmail(dto);

        assertEquals(newEmail, entity.getEmail());
        assertEquals(DisableReason.EMAIL_CONFIRMATION_REQUIRED, entity.getDisableReason());
        assertFalse(entity.isEnabled());
        
        verify(repository).save(entity);
        verify(cacheService).putEmailExistAs(newEmail, true);
        verify(cacheService).putEmailExistAs(oldEmail, false);
    }

    @Test
    @DisplayName("UT: updateEmail() when password mismatch should throw exception")
    void updateEmail_whenPasswordMismatch_shouldThrow() {
        UUID id = UUID.randomUUID();
        AccountEmailUpdateDto dto = new AccountEmailUpdateDto("wrong", "new@example.com");
        dto.setId(id);
        dto.setType(ProfileType.SPECIALIST);
        
        AccountEntity entity = new AccountEntity();
        entity.setPassword("encodedPass");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches("wrong", "encodedPass")).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> service.updateEmail(dto));
        verify(repository, never()).save(any());
    }
    
    @Test
    @DisplayName("UT: existsByEmail() should delegate to repository")
    void existsByEmail_shouldDelegate() {
        String email = "test@example.com";
        when(repository.existsByEmail(email)).thenReturn(true);
        assertTrue(service.existsByEmail(email));
        verify(repository).existsByEmail(email);
    }

    @Test
    @DisplayName("UT: findIdByEmail() should delegate to repository")
    void findIdByEmail_shouldDelegate() {
        String email = "test@example.com";
        UUID id = UUID.randomUUID();
        when(repository.findIdByEmail(email)).thenReturn(Optional.of(id));
        assertEquals(id, service.findIdByEmail(email));
    }
    
    @Test
    @DisplayName("UT: softDeleteById() should disable account")
    void softDeleteById_shouldDisable() {
        UUID id = UUID.randomUUID();
        service.softDeleteById(id);
        verify(repository).disableById(id, DisableReason.SOFT_DELETE);
    }
}
