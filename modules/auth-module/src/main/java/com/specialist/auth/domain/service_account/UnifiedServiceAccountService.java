package com.specialist.auth.domain.service_account;

import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.authority.AuthorityService;
import com.specialist.auth.domain.role.RoleEntity;
import com.specialist.auth.domain.role.RoleService;
import com.specialist.auth.domain.service_account.models.*;
import com.specialist.auth.exceptions.ServiceAccountNotFoundByIdException;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnifiedServiceAccountService implements ServiceAccountService, UserDetailsService {

    private final ServiceAccountRepository repository;
    private final ServiceAccountMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthorityService authorityService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        ServiceAccountEntity entity = repository.findById(UUID.fromString(id)).orElseThrow(ServiceAccountNotFoundByIdException::new);
        var authorities = new ArrayList<>(entity.getAuthorities().stream()
                .map(AuthorityEntity::getAuthority)
                .map(SimpleGrantedAuthority::new)
                .toList());
        authorities.add(new SimpleGrantedAuthority(entity.getRole().getAuthority()));
        return new ServiceAccountUserDetails(
                entity.getId(),
                entity.getSecret(),
                authorities
        );
    }

    @Transactional
    @Override
    public SecretServiceAccountResponseDto save(UUID adminId, ServiceAccountDto dto) {
        ServiceAccountEntity entity = Optional.ofNullable(dto.getId()).
                flatMap(repository::findById)
                .orElseGet(() -> {
                    ServiceAccountEntity newEntity = new ServiceAccountEntity();
                    newEntity.setCreatorId(adminId);
                    return newEntity;
                });

        RoleEntity roleEntity = roleService.getReferenceByRole(dto.getRole());
        List<AuthorityEntity> authorities = authorityService.getReferenceAllByAuthorityIn(dto.getAuthorities().stream().toList());

        String secret = generateSecret();
        entity.setSecret(passwordEncoder.encode(secret));
        entity.setRole(roleEntity);
        entity.setAuthorities(authorities);
        entity.setUpdaterId(adminId);
        entity = repository.save(entity);
        ServiceAccountResponseDto responseDto = mapper.toResponseDto(
                authorityService.findAllByServiceAccountIdIn(new HashSet<>(Set.of(entity.getId())))
                        .get(entity.getId()),
                entity
        );
        return new SecretServiceAccountResponseDto(secret, responseDto);
    }

    private String generateSecret() {
        byte [] secretBytes = new byte[48];
        new SecureRandom().nextBytes(secretBytes);
        return Base64.getUrlEncoder().encodeToString(secretBytes);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<ServiceAccountResponseDto> findAll(PageRequest pageRequest) {
        Page<ServiceAccountEntity> entityPage = repository.findAll(
                Pageable.ofSize(pageRequest.pageSize()).withPage(pageRequest.pageNumber())
        );
        List<ServiceAccountEntity> entityList = entityPage.getContent();
        Map<UUID, List<Authority>> authoritiesMap = authorityService.findAllByServiceAccountIdIn(entityList.stream()
                .map(ServiceAccountEntity::getId)
                .collect(Collectors.toSet())
        );
        List<ServiceAccountResponseDto> responseDtoList = new ArrayList<>();
        for (ServiceAccountEntity entity : entityList) {
            responseDtoList.add(mapper.toResponseDto(authoritiesMap.get(entity.getId()), entity));
        }
        return new PageResponse<>(
                responseDtoList,
                entityPage.getTotalPages()
        );
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new ServiceAccountNotFoundByIdException();
        }
        repository.deleteById(id);
    }}
