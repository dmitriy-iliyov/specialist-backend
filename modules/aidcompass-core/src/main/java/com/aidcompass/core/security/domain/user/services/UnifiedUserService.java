package com.aidcompass.core.security.domain.user.services;

import com.aidcompass.core.security.domain.authority.AuthorityService;
import com.aidcompass.core.security.domain.user.models.MemberUserDetails;
import com.aidcompass.core.security.domain.user.models.UserEntity;
import com.aidcompass.core.security.domain.user.models.dto.SystemUserUpdateDto;
import com.aidcompass.core.security.domain.user.models.dto.UserResponseDto;
import com.aidcompass.core.security.domain.authority.models.Authority;
import com.aidcompass.core.security.domain.authority.models.AuthorityEntity;
import com.aidcompass.core.security.domain.user.mapper.UserMapper;
import com.aidcompass.core.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.core.security.domain.user.models.dto.UserUpdateDto;
import com.aidcompass.core.security.domain.user.repositories.UserRepository;
import com.aidcompass.core.security.exceptions.illegal_input.IncorrectPasswordException;
import com.aidcompass.core.security.exceptions.not_found.UserNotFoundByEmailException;
import com.aidcompass.core.security.exceptions.not_found.UserNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnifiedUserService implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final AuthorityService authorityService;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = repository.findWithAuthorityByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not not found by " + email + "!"));
        return new MemberUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getAuthorities().stream()
                        .map(AuthorityEntity::getAuthority)
                        .map(Authority::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .toList(),
                userEntity.isExpired(),
                userEntity.isLocked()
        );
    }

    @Transactional
    @Override
    public void save(SystemUserDto systemUserDto) {
        UserEntity savedUserEntity = repository.save(mapper.toEntity(systemUserDto));
        AuthorityEntity authorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);
        savedUserEntity.getAuthorities().add(authorityEntity);
        repository.save(savedUserEntity);
    }

    @Cacheable(value = "users:exists", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Transactional
    @Override
    public UserResponseDto update(SystemUserUpdateDto dto) {
        UserEntity entity = repository.findById(dto.getId()).orElseThrow(
                UserNotFoundByIdException::new
        );
        mapper.updateEntityFromDto(dto, entity);
        entity.getAuthorities().clear();
        entity.getAuthorities().addAll(authorityService.toAuthorityEntityList(dto.getAuthorities()));
        return mapper.toResponseDto(repository.save(entity));
    }

    @Transactional
    @Override
    public void updatePasswordByEmail(String email, String password) {
        UserEntity userEntity = repository.findByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        userEntity.setPassword(passwordEncoder.encode(password));
        repository.save(userEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void confirmByEmail(String email, Long emailId) {
        UserEntity userEntity = repository.findWithAuthorityByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        AuthorityEntity newAuthorityEntity = authorityService.findByAuthority(Authority.ROLE_USER);

        List<AuthorityEntity> authorityEntities = userEntity.getAuthorities();
        authorityEntities.removeIf(auth -> auth.getAuthority().equals(Authority.ROLE_UNCONFIRMED_USER));

        if(!authorityEntities.contains(newAuthorityEntity)) {
            authorityEntities.add(newAuthorityEntity);
        }

        userEntity.setAuthorities(authorityEntities);
        userEntity.setEmailId(emailId);
        userEntity = repository.save(userEntity);

        redisTemplate.delete("users:exists::" + userEntity.getId());
    }

    @Transactional
    @Override
    public MemberUserDetails changeAuthorityById(UUID id, Authority authority) {
        AuthorityEntity authorityEntity = authorityService.findByAuthority(authority);
        UserEntity entity = repository.findWithAuthorityById(id).orElseThrow(UserNotFoundByIdException::new);
        List<AuthorityEntity> authorityEntityList = entity.getAuthorities();
        if (!authorityEntityList.contains(authorityEntity)) {
            authorityEntityList.clear();
            authorityEntityList.add(authorityEntity);
        }
        entity = repository.save(entity);

        return new MemberUserDetails(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getAuthorities().stream()
                        .map(AuthorityEntity::getAuthority)
                        .map(Authority::getAuthority)
                        .map(SimpleGrantedAuthority::new)
                        .toList(),
                entity.isExpired(),
                entity.isLocked()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public SystemUserDto systemFindById(UUID id) {
        UserEntity userEntity = repository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        return mapper.toSystemDto(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public SystemUserDto systemFindByEmail(String email) {
        UserEntity userEntity = repository.findByEmail(email).orElseThrow(UserNotFoundByEmailException::new);
        return mapper.toSystemDto(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findById(UUID id) {
        UserEntity userEntity = repository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        return mapper.toResponseDto(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SystemUserDto> findAllByIdIn(Set<UUID> ids) {
        return mapper.toSystemDtoList(repository.findAllByIdIn(ids));
    }

    @CacheEvict(value = "users:exists", key = "#id")
    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @CacheEvict(value = "users:exists", key = "#id")
    @Transactional
    @Override
    public void deleteByPassword(UUID id, String password) {
        UserEntity userEntity = repository.findById(id).orElseThrow(UserNotFoundByIdException::new);
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            repository.deleteById(id);
            return;
        } throw new IncorrectPasswordException();
    }

    @Override
    public SystemUserUpdateDto mapToUpdateDto(UserUpdateDto userUpdateDto) {
        return mapper.toSystemUpdateDto(userUpdateDto);
    }
}