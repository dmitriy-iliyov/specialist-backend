package com.aidcompass.auth.domain.account.services;

import com.aidcompass.auth.domain.account.AccountMapper;
import com.aidcompass.auth.domain.account.AccountRepository;
import com.aidcompass.auth.domain.account.models.AccountCreateDto;
import com.aidcompass.auth.domain.account.models.AccountEntity;
import com.aidcompass.auth.domain.account.models.AccountResponseDto;
import com.aidcompass.auth.domain.account.models.AccountUserDetails;
import com.aidcompass.auth.domain.authority.AuthorityEntity;
import com.aidcompass.auth.domain.role.RoleEntity;
import com.aidcompass.auth.exceptions.AccountNotFoundByEmailException;
import com.aidcompass.auth.exceptions.AccountNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

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
        return new AccountUserDetails(entity.getEmail(), entity.getPassword(), authorities, entity.isLocked(), entity.isEnabled());
    }

    @Transactional
    @Override
    public AccountResponseDto save(AccountCreateDto dto) {
        AccountEntity entity = new AccountEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        RoleEntity role = roleService.findByRole(dto.getRole());
        entity.setRole(role);
        List<AuthorityEntity> authorities = authorityService.findAllByAuthorityIn(dto.getAuthorities());
        entity.setAuthorities(authorities);
        return mapper.toResponseDto(repository.save(entity));
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

    
    public void confirmEmailById(UUID id) {
        repository.enableById(id);
    }

    @Transactional
    @Override
    public void updateEmailById(UUID id, String email) {
        AccountEntity entity = repository.findById(id).orElseThrow(AccountNotFoundByIdException::new);
        entity.setEmail(email);
        entity.setEnabled(false);
        repository.save(entity);
    }

    @Transactional
    @Override
    public void lockById(UUID id) {
        repository.lockById(id);
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
