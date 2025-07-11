package com.aidcompass.core.security.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceUserDetailsService implements UserDetailsService {

    private final ServiceRepository repository;


    @Override
    public UserDetails loadUserByUsername(String serviceName) throws UsernameNotFoundException {
        ServiceEntity entity = repository.findByServiceName(serviceName).orElseThrow(
                () -> new UsernameNotFoundException("Service " + serviceName + " not found!")
        );
        return new ServiceUserDetails(
                entity.getId(),
                entity.getServiceName(),
                entity.getPassword(),
                List.of(new SimpleGrantedAuthority(entity.getAuthorityEntity().getAuthority().toString())),
                entity.isLocked()
        );
    }
}
