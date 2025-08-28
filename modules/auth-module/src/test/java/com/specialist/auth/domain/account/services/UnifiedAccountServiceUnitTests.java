package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.oauth2.provider.Provider;
import com.specialist.auth.domain.account.mappers.AccountMapper;
import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.*;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityService;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.role.RoleService;
import com.specialist.auth.exceptions.AccountNotFoundByEmailException;
import com.specialist.auth.exceptions.AccountNotFoundByIdException;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnifiedAccountServiceUnitTests {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper mapper;

    @Mock
    private RoleService roleService;

    @Mock
    private AuthorityService authorityService;

    @Mock
    private AccountCacheService cacheService;

    @InjectMocks
    private UnifiedAccountService unifiedAccountService;

    @AfterEach
    void tearDown() throws Exception {
        verifyNoMoreInteractions(repository, passwordEncoder, mapper, roleService, authorityService);
    }

    @Test
    @DisplayName("UT: loadUserByUsername() when email exists returns UserDetails")
    void loadUserByUsername_whenEmailExists_shouldReturnUserDetails() {
        String email = "user@example.com";
        UUID id = UUID.randomUUID();

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(Role.ROLE_USER);

        AuthorityEntity authEntity = new AuthorityEntity();
        authEntity.setAuthority(Authority.REVIEW_CREATE_UPDATE);

        AccountEntity entity = new AccountEntity();
        entity.setId(id);
        entity.setEmail(email);
        entity.setPassword("encodedPass");
        entity.setRole(roleEntity);
        entity.setAuthorities(List.of(authEntity));
        entity.setLocked(false);
        entity.setEnabled(true);

        when(repository.findByEmail(email)).thenReturn(Optional.of(entity));

        UserDetails userDetails = unifiedAccountService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
        assertEquals("encodedPass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("REVIEW_CREATE_UPDATE")));

        verify(repository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("UT: loadUserByUsername() when email not found throws exception")
    void loadUserByUsername_whenEmailNotFound_shouldThrow() {
        String email = "missing@example.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundByEmailException.class, () -> {
            unifiedAccountService.loadUserByUsername(email);
        });

        verify(repository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("UT: save(DefaultAccountCreateDto.class) persists account with encoded password and disables it")
    void saveDefault_shouldSaveAccountProperly() {
        var dto = new DefaultAccountCreateDto("email@example.com", "rawPass");

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(Role.ROLE_USER);


        List<AuthorityEntity> authEntities = List.of(new AuthorityEntity(), new AuthorityEntity());

        AccountEntity savedEntity = new AccountEntity();
        savedEntity.setId(UUID.randomUUID());

        when(roleService.getReferenceByRole(dto.getRole())).thenReturn(roleEntity);
        when(authorityService.getReferenceAllByAuthorityIn(dto.getAuthorities())).thenReturn(authEntities);
        when(passwordEncoder.encode("rawPass")).thenReturn("encodedPass");
        when(repository.save(any())).thenReturn(savedEntity);
        when(mapper.toShortResponseDto(savedEntity)).thenReturn(new ShortAccountResponseDto(savedEntity.getId(), dto.getEmail(), LocalDateTime.now()));
        doNothing().when(cacheService).putEmailAsTrue(anyString());

        var response = unifiedAccountService.save(dto);

        ArgumentCaptor<AccountEntity> captor = ArgumentCaptor.forClass(AccountEntity.class);
        verify(repository).save(captor.capture());
        AccountEntity entitySaved = captor.getValue();

        assertEquals(dto.getEmail(), entitySaved.getEmail());
        assertEquals("encodedPass", entitySaved.getPassword());
        assertFalse(entitySaved.isEnabled());
        assertEquals(DisableReason.EMAIL_CONFIRMATION_REQUIRED, entitySaved.getDisableReason());
        assertEquals(roleEntity, entitySaved.getRole());
        assertEquals(authEntities, entitySaved.getAuthorities());

        verify(roleService, times(1)).getReferenceByRole(dto.getRole());
        verify(authorityService, times(1)).getReferenceAllByAuthorityIn(dto.getAuthorities());
        verify(passwordEncoder, times(1)).encode("rawPass");
        verify(mapper, times(1)).toShortResponseDto(savedEntity);
        verify(cacheService, times(1)).putEmailAsTrue(anyString());

        assertNotNull(response);
    }

    @Test
    @DisplayName("UT: save() persists account with encoded password and disables it")
    void saveOAuth2_shouldSaveAccountProperly() {
        var dto = new OAuth2AccountCreateDto("email@example.com", Provider.GOOGLE, Role.ROLE_USER, List.of(Authority.REVIEW_CREATE_UPDATE));

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(Role.ROLE_USER);

        List<AuthorityEntity> authEntities = List.of(new AuthorityEntity(), new AuthorityEntity());

        AccountEntity savedEntity = new AccountEntity();
        savedEntity.setId(UUID.randomUUID());

        when(roleService.getReferenceByRole(dto.role())).thenReturn(roleEntity);
        when(authorityService.getReferenceAllByAuthorityIn(dto.authorities())).thenReturn(authEntities);
        when(repository.save(any())).thenReturn(savedEntity);
        when(mapper.toShortResponseDto(savedEntity)).thenReturn(new ShortAccountResponseDto(savedEntity.getId(), dto.email(), LocalDateTime.now()));

        var response = unifiedAccountService.save(dto);

        ArgumentCaptor<AccountEntity> captor = ArgumentCaptor.forClass(AccountEntity.class);
        verify(repository).save(captor.capture());
        AccountEntity entitySaved = captor.getValue();

        assertEquals(dto.email(), entitySaved.getEmail());
        assertTrue(entitySaved.isEnabled());
        assertTrue(entitySaved.getPassword() == null);
        assertEquals(roleEntity, entitySaved.getRole());
        assertEquals(authEntities, entitySaved.getAuthorities());

        verify(roleService, times(1)).getReferenceByRole(dto.role());
        verify(authorityService, times(1)).getReferenceAllByAuthorityIn(dto.authorities());
        verify(mapper, times(1)).toShortResponseDto(savedEntity);

        assertNotNull(response);
    }

    @Test
    @DisplayName("UT: existsByEmail() when email exists should return true")
    void existsByEmail_whenExists_shouldReturnTrue() {
        when(repository.existsByEmail("test@mail.com")).thenReturn(true);

        boolean result = unifiedAccountService.existsByEmail("test@mail.com");

        verify(repository, times(1)).existsByEmail("test@mail.com");
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper, passwordEncoder);
        assertTrue(result);
    }

    @Test
    @DisplayName("UT: existsByEmail() when email does not exist should return false")
    void existsByEmail_whenNotExists_shouldReturnFalse() {
        when(repository.existsByEmail("test@mail.com")).thenReturn(false);

        boolean result = unifiedAccountService.existsByEmail("test@mail.com");

        verify(repository, times(1)).existsByEmail("test@mail.com");
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper, passwordEncoder);
        assertFalse(result);
    }

    @Test
    @DisplayName("UT: findIdByEmail() when email found should return UUID")
    void findIdByEmail_whenFound_shouldReturnUUID() {
        UUID expectedId = UUID.randomUUID();
        when(repository.findIdByEmail("test@mail.com")).thenReturn(Optional.of(expectedId));

        UUID result = unifiedAccountService.findIdByEmail("test@mail.com");

        verify(repository, times(1)).findIdByEmail("test@mail.com");
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper, passwordEncoder);
        assertEquals(expectedId, result);
    }

    @Test
    @DisplayName("UT: findIdByEmail() when email not found should return null")
    void findIdByEmail_whenNotFound_shouldReturnNull() {
        when(repository.findIdByEmail("test@mail.com")).thenReturn(Optional.empty());

        UUID result = unifiedAccountService.findIdByEmail("test@mail.com");

        verify(repository, times(1)).findIdByEmail("test@mail.com");
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper, passwordEncoder);
        assertNull(result);
    }

    @Test
    @DisplayName("UT: confirmEmail() should call repository.enableByEmail() once")
    void confirmEmail_shouldCallRepository() {
        unifiedAccountService.confirmEmail("test@mail.com");

        verify(repository, times(1)).enableByEmail("test@mail.com");
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper, passwordEncoder);
    }

    @Test
    @DisplayName("UT: updatePassword() when account exists should update password and return dto")
    void updatePassword_whenAccountExists_shouldUpdatePasswordPasswordAndReturnDto() {
        UUID id = UUID.randomUUID();
        AccountPasswordUpdateDto dto = new AccountPasswordUpdateDto("oldpass", "newpass");
        dto.setId(id);
        AccountEntity entity = new AccountEntity();
        entity.setId(id);
        ShortAccountResponseDto expectedDto = new ShortAccountResponseDto(id, "mail@mail.com", LocalDateTime.now());

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(passwordEncoder.matches("oldpass", anyString())).thenReturn(true);
        when(passwordEncoder.encode("newpass")).thenReturn("encoded");
        when(mapper.toShortResponseDto(entity)).thenReturn(expectedDto);

        ShortAccountResponseDto result = unifiedAccountService.updatePassword(dto);

        verify(repository, times(1)).findById(id);
        verify(passwordEncoder, times(1)).encode("newpass");
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toShortResponseDto(entity);
        verifyNoMoreInteractions(repository, mapper, passwordEncoder);
        assertEquals(expectedDto, result);
        assertEquals("encoded", entity.getPassword());
    }

    @Test
    @DisplayName("UT: updatePassword() when account not found should throw exception")
    void updatePassword_whenNotFound_shouldThrowException() {
        UUID id = UUID.randomUUID();
        AccountPasswordUpdateDto dto = new AccountPasswordUpdateDto("oldpassword", "newpass");
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundByIdException.class, () -> unifiedAccountService.updatePassword(dto));

        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper, passwordEncoder);
    }

    @Test
    @DisplayName("UT: updatePasswordByEmail() updates password correctly")
    void updatePasswordByEmail_shouldRecoverPasswordPassword() {
        String email = "user@example.com";
        String rawPassword = "newpass";
        String encodedPassword = "encodedpass";

        AccountEntity entity = new AccountEntity();
        entity.setEmail(email);

        when(repository.findByEmail(email)).thenReturn(Optional.of(entity));
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        unifiedAccountService.recoverPasswordByEmail(email, rawPassword);

        assertEquals(encodedPassword, entity.getPassword());

        verify(repository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }

    @Test
    @DisplayName("UT: updatePasswordByEmail() throws exception when email not found")
    void updatePasswordPasswordByEmail_whenEmailNotFound_shouldThrow() {
        String email = "missing@example.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundByEmailException.class, () -> unifiedAccountService.recoverPasswordByEmail(email, "pass"));

        verify(repository, times(1)).findByEmail(email);
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    @DisplayName("UT: deleteById() delegates to repository")
    void deleteById_shouldCallRepository() {
        UUID id = UUID.randomUUID();

        doNothing().when(repository).deleteById(id);

        unifiedAccountService.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("UT: findAll() returns paged response")
    void findAll_shouldReturnPageResponse() {
        var pageRequest = new com.specialist.utils.pagination.PageRequest(0, 10, true);

        AccountEntity entity = new AccountEntity();
        entity.setId(UUID.randomUUID());

        Page<AccountEntity> page = new PageImpl<>(List.of(entity), PageRequest.of(0, 10), 1);

        Map<UUID, List<Authority>> authoritiesMap = Map.of(entity.getId(), List.of(Authority.REVIEW_CREATE_UPDATE));

        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        when(authorityService.findAllByAccountIdIn(Set.of(entity.getId()))).thenReturn(authoritiesMap);
        when(mapper.toResponseDto(any(), eq(entity))).thenReturn(new AccountResponseDto(
                UUID.randomUUID(),
                "user@example.com",
                "encodedPassword",
                Role.ROLE_USER,
                List.of(Authority.REVIEW_CREATE_UPDATE),
                false,
                null,
                null,
                true,
                null,
                Instant.now(),
                Instant.now()
        ));

        PageResponse<AccountResponseDto> response = unifiedAccountService.findAll(pageRequest);

        assertNotNull(response);
        assertEquals(1, response.totalPages());

        verify(repository, times(1)).findAll(any(Pageable.class));
        verify(authorityService, times(1)).findAllByAccountIdIn(Set.of(entity.getId()));
        verify(mapper, times(1)).toResponseDto(any(), eq(entity));
    }

    @Test
    @DisplayName("UT: findAllByFilter() returns filtered paged response")
    void findAllByFilter_shouldReturnFilteredPageResponse() {
        AccountFilter filter = new AccountFilter(true, "ABUSE", false,
                "PERMANENTLY_SPAM",  0, 20, true
        );

        AccountEntity entity = new AccountEntity();
        entity.setId(UUID.randomUUID());

        Page<AccountEntity> page = new PageImpl<>(List.of(entity), PageRequest.of(0, 10), 1);
        Map<UUID, List<Authority>> authoritiesMap = Map.of(entity.getId(), List.of(Authority.REVIEW_CREATE_UPDATE));

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(authorityService.findAllByAccountIdIn(Set.of(entity.getId()))).thenReturn(authoritiesMap);
        when(mapper.toResponseDto(any(), eq(entity))).thenReturn(new AccountResponseDto(
                UUID.randomUUID(),
                "user@example.com",
                "encodedPassword",
                Role.ROLE_USER,
                List.of(Authority.REVIEW_CREATE_UPDATE),
                false,
                null,
                null,
                true,
                null,
                Instant.now(),
                Instant.now()
        ));

        PageResponse<AccountResponseDto> response = unifiedAccountService.findAllByFilter(filter);

        assertNotNull(response);
        assertEquals(1, response.totalPages());

        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(authorityService, times(1)).findAllByAccountIdIn(Set.of(entity.getId()));
        verify(mapper, times(1)).toResponseDto(any(), eq(entity));
    }

    @Test
    @DisplayName("UT: lockById() calls repository with correct params")
    void lockById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        var lockRequest = new LockRequest(LockReason.REVIEW_SPAM, LocalDateTime.now());

        doNothing().when(repository).lockById(
                id,
                lockRequest.reason(),
                lockRequest.term().atZone(ZoneId.systemDefault()).toInstant()
        );

        unifiedAccountService.lockById(id, lockRequest);

        verify(repository, times(1)).lockById(
                id,
                lockRequest.reason(),
                lockRequest.term().atZone(ZoneId.systemDefault()).toInstant()
        );
    }

    @Test
    @DisplayName("UT: setUnableById() calls repository with correct params")
    void setUnableById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        var unableRequest = new DisableRequest(DisableReason.EMAIL_CONFIRMATION_REQUIRED);

        doNothing().when(repository).disableById(id, DisableReason.EMAIL_CONFIRMATION_REQUIRED);

        unifiedAccountService.disableById(id, unableRequest);

        verify(repository, times(1)).disableById(id, DisableReason.EMAIL_CONFIRMATION_REQUIRED);
    }
}

