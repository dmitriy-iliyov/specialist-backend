package com.specialist.auth.domain.service_account;

import com.specialist.auth.domain.authority.AuthorityEntity;
import com.specialist.auth.domain.service_account.models.ServiceAccountEntity;
import com.specialist.auth.domain.service_account.models.ServiceAccountUserDetails;
import com.specialist.auth.exceptions.ServiceAccountNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceAccountUserDetailsService implements UserDetailsService {

    private final ServiceAccountRepository repository;

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
}
