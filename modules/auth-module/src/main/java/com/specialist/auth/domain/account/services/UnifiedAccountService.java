package com.specialist.auth.domain.account.services;

import com.specialist.auth.core.oauth2.provider.Provider;
import com.specialist.auth.domain.account.mappers.AccountMapper;
import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.AccountFilter;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.account.models.dtos.*;
import com.specialist.auth.domain.account.models.enums.DisableReason;
import com.specialist.auth.domain.account.models.enums.LockReason;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.account.repositories.AccountSpecification;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityService;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.role.RoleService;
import com.specialist.auth.exceptions.AccountNotFoundByEmailException;
import com.specialist.auth.exceptions.AccountNotFoundByIdException;
import com.specialist.auth.exceptions.InvalidOldPasswordException;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnifiedAccountService implements AccountService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final AccountCacheService cacheService;
    private final RoleService roleService;
    private final AuthorityService authorityService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountEntity entity = repository.findByEmail(email).orElseThrow(AccountNotFoundByEmailException::new);
        var authorities = new ArrayList<>(entity.getAuthorities().stream()
                .map(AuthorityEntity::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .toList());
        authorities.add(new SimpleGrantedAuthority(entity.getRole().getAuthority()));
        return new AccountUserDetails(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getProvider(),
                authorities,
                entity.isLocked(),
                entity.getLockReason(),
                entity.getLockTerm(),
                entity.isEnabled(),
                entity.getDisableReason()
        );
    }

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
        cacheService.putEmailAsTrue(responseDto.email());
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
        return mapper.toShortResponseDto(repository.save(entity));
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
    public void confirmEmail(String email) {
        repository.enableByEmail(email);
    }

    @Transactional
    @Override
    public ShortAccountResponseDto updatePassword(AccountPasswordUpdateDto dto) {
        AccountEntity entity = repository.findById(dto.getId()).orElseThrow(AccountNotFoundByIdException::new);
        if (!passwordEncoder.matches(dto.getOldPassword(), entity.getPassword())) {
            throw new InvalidOldPasswordException("old_password");
        }
        entity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repository.save(entity);
        return mapper.toShortResponseDto(entity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public ShortAccountResponseDto updateEmail(AccountEmailUpdateDto dto) {
        AccountEntity entity = repository.findById(dto.getId()).orElseThrow(AccountNotFoundByIdException::new);
        if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new InvalidOldPasswordException("password");
        }
        entity.setEmail(entity.getEmail());
        entity.setEnabled(false);
        entity.setDisableReason(DisableReason.EMAIL_CONFIRMATION_REQUIRED);
        repository.save(entity);
        return mapper.toShortResponseDto(entity);
    }

    @Transactional
    @Override
    public void recoverPasswordByEmail(String email, String password) {
        AccountEntity entity = repository.findByEmail(email).orElseThrow(AccountNotFoundByEmailException::new);
        entity.setPassword(passwordEncoder.encode(password));
    }

    @Transactional
    @Override
    public void takeAwayAuthoritiesById(UUID id, Set<Authority> authorities) {
        AccountEntity accountEntity = repository.findById(id).orElseThrow(AccountNotFoundByIdException::new);
        accountEntity.getAuthorities().removeIf(entity -> authorities.contains(entity.getAuthorityAsEnum()));
        repository.save(accountEntity);
    }

    @Transactional
    @Override
    public void lockById(UUID id, LockRequest request) {
        repository.lockById(id, request.reason(), request.term().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Transactional
    @Override
    public void unlockById(UUID id) {
        repository.unlockById(id);
    }

    @Transactional
    @Override
    public void disableById(UUID id, DisableRequest request) {
        repository.disableById(id, request.reason());
    }

    @Transactional
    @Override
    public void enableById(UUID id) {
        repository.enableById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Provider findProviderByEmail(String email) {
        return repository.findProviderByEmail(email).orElseThrow(AccountNotFoundByEmailException::new);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<AccountResponseDto> findAll(com.specialist.utils.pagination.PageRequest pageRequest) {
        Page<AccountEntity> entityPage = repository.findAll(generatePageable(pageRequest));
        Map<UUID, List<Authority>> authoritiesMap = loadAuthorities(entityPage.getContent());
        return toPageResponse(entityPage, authoritiesMap);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<AccountResponseDto> findAllByFilter(AccountFilter filter) {
        Specification<AccountEntity> specification = Specification.where(AccountSpecification.filterByIsLocked(filter.locked()))
                .and(AccountSpecification.filterByLockeReason(LockReason.valueOf(filter.lockReason())))
                .and(AccountSpecification.filterByIsEnable(filter.enable()))
                .and(AccountSpecification.filterByUnableReason(DisableReason.valueOf(filter.disableReason())));
        Page<AccountEntity> entityPage = repository.findAll(specification, generatePageable(filter));
        Map<UUID, List<Authority>> authoritiesMap = loadAuthorities(entityPage.getContent());
        return toPageResponse(entityPage, authoritiesMap);
    }

    private Pageable generatePageable(PageDataHolder holder) {
        if (holder.asc() != null && holder.asc().equals(Boolean.TRUE)) {
            return PageRequest.of(holder.pageNumber(), holder.pageSize(), Sort.by("createdAt").ascending());
        }
        return PageRequest.of(holder.pageNumber(), holder.pageSize(), Sort.by("createdAt").descending());
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
