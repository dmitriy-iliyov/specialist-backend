package com.aidcompass.core.security.domain.service;

import com.aidcompass.core.general.utils.uuid.UuidFactory;
import com.aidcompass.core.security.domain.authority.AuthorityService;
import com.aidcompass.core.security.domain.authority.models.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class ServiceDatabaseInitializer {

    private final ServiceRepository repository;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void setUp() {
        repository.save(new ServiceEntity(
                UuidFactory.generate(),
                "service-name",
                passwordEncoder.encode("service-key"),
                authorityService.findByAuthority(Authority.ROLE_ANONYMOUS),
                Instant.now(),
                Instant.now(),
                false)
        );
    }
}