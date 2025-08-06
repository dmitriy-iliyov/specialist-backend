package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.models.dtos.*;
import com.aidcompass.auth.domain.account.models.enums.LockReason;
import com.aidcompass.auth.domain.account.models.enums.UnableReason;
import com.aidcompass.auth.domain.account.repositories.AccountRepository;
import com.aidcompass.auth.domain.account.mappers.AccountMapper;
import com.aidcompass.auth.domain.account.models.*;
import com.aidcompass.auth.domain.account.repositories.AccountSpecification;
import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.authority.AuthorityEntity;
import com.aidcompass.auth.domain.authority.AuthorityService;
import com.aidcompass.auth.domain.role.RoleEntity;
import com.aidcompass.auth.domain.role.RoleService;
import com.aidcompass.auth.exceptions.AccountNotFoundByEmailException;
import com.aidcompass.auth.exceptions.AccountNotFoundByIdException;
import com.aidcompass.auth.exceptions.NonUniqueEmailException;
import com.aidcompass.utils.pagination.PageDataHolder;
import com.aidcompass.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnifiedAccountService implements AccountService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository repository;
    private final AccountMapper mapper;
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
                authorities,
                entity.isLocked(),
                entity.isEnabled()
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
        entity.setRole(role);
        entity.setAuthorities(authorities);
        entity.setEnabled(false);
        entity.setUnableReason(UnableReason.EMAIL_CONFIRMATION_REQUIRED);
        return mapper.toShortResponseDto(repository.save(entity));
    }

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
    public ShortAccountResponseDto update(AccountUpdateDto dto) {
        AccountEntity entity = repository.findById(dto.getId()).orElseThrow(AccountNotFoundByIdException::new);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(entity);
        return mapper.toShortResponseDto(entity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void updateEmailById(UUID id, String email) {
        AccountEntity entity = repository.findById(id).orElseThrow(AccountNotFoundByIdException::new);
        Optional<AccountEntity> existsByEmail = repository.findByEmail(email);
        if (existsByEmail.isPresent() && !existsByEmail.get().getId().equals(id)) {
            throw new NonUniqueEmailException();
        }
        entity.setEmail(email);
        entity.setEnabled(false);
        entity.setUnableReason(UnableReason.EMAIL_CONFIRMATION_REQUIRED);
        repository.save(entity);
    }

    @Transactional
    @Override
    public void updatePasswordByEmail(String email, String password) {
        AccountEntity entity = repository.findByEmail(email).orElseThrow(AccountNotFoundByEmailException::new);
        entity.setPassword(passwordEncoder.encode(password));

    }

    @Transactional
    @Override
    public void lockById(UUID id, LockDto dto) {
        repository.lockById(id, dto.reason(), dto.term());
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<AccountResponseDto> findAll(com.aidcompass.utils.pagination.PageRequest pageRequest) {
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
                .and(AccountSpecification.filterByUnableReason(UnableReason.valueOf(filter.unableReason())));
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
