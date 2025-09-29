package com.specialist.auth.core.oauth2.services;

import com.specialist.auth.core.oauth2.OAuth2UserRepository;
import com.specialist.auth.core.oauth2.mappers.OAuth2UserMapper;
import com.specialist.auth.core.oauth2.models.OAuth2UserEntity;
import com.specialist.auth.core.oauth2.models.Provider;
import com.specialist.auth.domain.account.services.AccountPersistOrchestrator;
import com.specialist.auth.domain.account.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuth2AccountPersistOrchestratorImpl implements OAuth2AccountPersistOrchestrator {

    private final AccountService service;
    private final AccountPersistOrchestrator persistOrchestrator;
    private final OAuth2UserMapper mapper;
    private final OAuth2UserRepository repository;

    @Value("${api.oauth-user.ttl}")
    public Long TTL;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void saveIfNonExists(Provider provider, OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        if (email != null && !service.existsByEmail(email)) {
            UUID accountId = persistOrchestrator.save(email, provider).id();
            OAuth2UserEntity entity = mapper.toEntity(provider, accountId, oAuth2User);
            entity.setTtl(Duration.ofSeconds(TTL).getSeconds());
            repository.save(entity);
        }
    }
}
