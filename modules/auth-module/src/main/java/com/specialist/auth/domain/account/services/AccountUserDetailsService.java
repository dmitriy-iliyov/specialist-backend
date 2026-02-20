package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.AccountUserDetails;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.exceptions.AccountNotFoundByEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {

    private final AccountRepository repository;

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
}
