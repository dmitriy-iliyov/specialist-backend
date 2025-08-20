package com.specialist.auth.core.oauth2.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ClientRegistrationRepository repository;

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
}
