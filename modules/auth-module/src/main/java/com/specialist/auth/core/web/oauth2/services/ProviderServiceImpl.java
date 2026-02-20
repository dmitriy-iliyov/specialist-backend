package com.specialist.auth.core.web.oauth2.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ClientRegistrationRepository repository;
    private final String BASE_AUTHORIZATION_URL = "https://localhost:8443/api/auth/oauth2/authorize?provider=%s";

    @Override
    @Cacheable(value = "providers")
    public List<String> findAll() {
        List<String> providers = new ArrayList<>();
        if (repository instanceof InMemoryClientRegistrationRepository inMemoryRepository) {
            for (ClientRegistration registration: inMemoryRepository) {
                providers.add(registration.getRegistrationId());
            }
        }
        return providers;
    }

    @Override
    @Cacheable(value = "providers:paths")
    public Map<String, String> findAllPaths() {
        Map<String, String> providers = new HashMap<>();
        if (repository instanceof InMemoryClientRegistrationRepository inMemoryRepository) {
            for (ClientRegistration registration: inMemoryRepository) {
                providers.put(
                        registration.getRegistrationId(),
                        BASE_AUTHORIZATION_URL.formatted(registration.getRegistrationId())
                );
            }
        }
        return providers;
    }
}
