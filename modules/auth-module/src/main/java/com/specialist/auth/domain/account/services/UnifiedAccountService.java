package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.domain.account.mappers.AccountMapper;
import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.dtos.*;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.account.repositories.AccountSpecification;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityService;
import com.specialist.auth.domain.role.Role;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.role.RoleService;
import com.specialist.auth.exceptions.AccountNotFoundByEmailException;
import com.specialist.auth.exceptions.AccountNotFoundByIdException;
import com.specialist.auth.exceptions.InvalidPasswordException;
import com.specialist.auth.exceptions.RoleNotFoundException;
import com.specialist.contracts.auth.DeferAccountDeleteEvent;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnifiedAccountService implements AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final AccountCacheService cacheService;
    private final RoleService roleService;
    private final AuthorityService authorityService;

    @Transactional
    @Override
    public ShortAccountResponseDto save(DefaultAccountCreateDto dto) {
        RoleEntity role = roleService.getReferenceByRole(dto.getRole());
        List<AuthorityEntity> authorities = authorityService.getReferenceAllByAuthorityIn(dto.getAuthorities());
        AccountEntity entity = new AccountEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setProvider(Provider.LOCAL);
        entity.setRole(role);
        entity.setAuthorities(authorities);
        entity.setLocked(false);
        entity.setEnabled(false);
        entity.setDisableReason(DisableReason.EMAIL_CONFIRMATION_REQUIRED);
        ShortAccountResponseDto responseDto = mapper.toShortResponseDto(repository.save(entity));
        cacheService.putEmailExistAs(responseDto.email(), Boolean.TRUE);
        return responseDto;
    }

    @Transactional
    @Override
    public ShortAccountResponseDto save(OAuth2AccountCreateDto dto) {
        RoleEntity role = roleService.getReferenceByRole(dto.role());
        List<AuthorityEntity> authorities = authorityService.getReferenceAllByAuthorityIn(dto.authorities());
        AccountEntity entity = new AccountEntity();
        entity.setEmail(dto.email());
        entity.setProvider(dto.provider());
        entity.setRole(role);
        entity.setAuthorities(authorities);
        entity.setLocked(false);
        entity.setEnabled(true);
        entity = repository.save(entity);
        cacheService.putEmailExistAs(entity.getEmail(), Boolean.TRUE);
        return mapper.toShortResponseDto(entity);
    }

    @Cacheable(value = "accounts:emails", key = "#email")
    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public UUID findIdByEmail(String email) {
        return repository.findIdByEmail(email).orElse(null);
    }

    @Transactional
    @Override
    public ShortAccountResponseDto updatePassword(AccountPasswordUpdateDto dto) {
        AccountEntity entity = repository.findById(dto.getId()).orElseThrow(AccountNotFoundByIdException::new);
        if (!passwordEncoder.matches(dto.getOldPassword(), entity.getPassword())) {
            throw new InvalidPasswordException("old_password");
        }
        entity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repository.save(entity);
        return mapper.toShortResponseDto(entity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public ShortAccountResponseDto updateEmail(AccountEmailUpdateDto dto) {
        AccountEntity entity = repository.findById(dto.getId()).orElseThrow(AccountNotFoundByIdException::new);
        String oldEmail = entity.getEmail();
        if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new InvalidPasswordException("password");
        }
        entity.setEmail(dto.getEmail());
        entity.setEnabled(false);
        entity.setDisableReason(DisableReason.EMAIL_CONFIRMATION_REQUIRED);
        repository.save(entity);
        cacheService.putEmailExistAs(entity.getEmail(), Boolean.TRUE);
        cacheService.putEmailExistAs(oldEmail, Boolean.FALSE);
        return mapper.toShortResponseDto(entity);
    }

    @Transactional
    @Override
    public ShortAccountResponseDto updateRoleAndAuthoritiesById(UUID id, Role role, Set<Authority> authorities) {
        AccountEntity accountEntity = repository.findByIdWithRoleAndAuthorities(id).orElseThrow(
                AccountNotFoundByIdException::new
        );
        accountEntity.setRole(roleService.getReferenceByRole(role));
        accountEntity.setAuthorities(authorityService.getReferenceAllByAuthorityIn(authorities.stream().toList()));
        return mapper.toShortResponseDto(repository.save(accountEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public Role findRoleById(UUID id) {
        return repository.findRoleById(id)
                .orElseThrow(RoleNotFoundException::new)
                .getRole();
    }

    @Transactional(readOnly = true)
    @Override
    public Provider findProviderByEmail(String email) {
        return repository.findProviderByEmail(email).orElseThrow(AccountNotFoundByEmailException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public String findEmailById(UUID id) {
        return repository.findEmailById(id).orElseThrow(AccountNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<AccountResponseDto> findAllByFilter(AccountFilter filter) {
        Specification<AccountEntity> nullableSpecification = Specification.unrestricted();
        Specification<AccountEntity> specification = nullableSpecification
                .and(AccountSpecification.filterByIsLocked(filter.getLocked()))
                .and(AccountSpecification.filterByLockeReason(filter.getLockReason()))
                .and(AccountSpecification.filterByIsEnable(filter.getEnable()))
                .and(AccountSpecification.filterByUnableReason(filter.getDisableReason()));
        Page<AccountEntity> entityPage = repository.findAll(specification, generatePageable(filter));
        Map<UUID, List<Authority>> authoritiesMap = loadAuthorities(entityPage.getContent());
        return toPageResponse(entityPage, authoritiesMap);
    }

    @Transactional
    @Override
    public List<DeferAccountDeleteEvent> findAllByDisableReasonAndThreshold(DisableReason disableReason, Instant threshold, int batchSize) {
        return repository.findAllByDisableReasonAndThreshold(disableReason.getCode(), threshold, batchSize)
                .stream()
                .map(projection -> new DeferAccountDeleteEvent(projection.getId(), projection.getRole().toString()))
                .toList();
    }

    @Transactional
    @Override
    public void softDeleteById(UUID id) {
        repository.disableById(id, DisableReason.SOFT_DELETE);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllByIdIn(Set<UUID> ids) {
        repository.deleteAllByIdIn(ids);
    }

    private Pageable generatePageable(PageDataHolder holder) {
        if (holder.isAsc() != null && holder.isAsc().equals(Boolean.TRUE)) {
            return PageRequest.of(holder.getPageNumber(), holder.getPageSize(), Sort.by("createdAt").ascending());
        }
        return PageRequest.of(holder.getPageNumber(), holder.getPageSize(), Sort.by("createdAt").descending());
    }

    private Map<UUID, List<Authority>> loadAuthorities(List<AccountEntity> entityList) {
        return authorityService.findAllByAccountIdIn(entityList.stream()
                .map(AccountEntity::getId)
                .collect(Collectors.toSet())
        );
    }

    private PageResponse<AccountResponseDto> toPageResponse(Page<AccountEntity> entityPage,
                                                            Map<UUID, List<Authority>> authoritiesMap) {
        List<AccountEntity> entityList = entityPage.getContent();
        List<AccountResponseDto> resultList = new ArrayList<>();
        for (AccountEntity entity: entityList) {
            resultList.add(mapper.toResponseDto(authoritiesMap.get(entity.getId()), entity));
        }
        return new PageResponse<>(
                resultList,
                entityPage.getTotalPages()
        );
    }
}
