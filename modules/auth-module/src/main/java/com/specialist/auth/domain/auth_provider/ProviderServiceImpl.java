package com.specialist.auth.domain.auth_provider;

import com.specialist.auth.core.security_filter_chain.AccountSecurityFilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ClientRegistrationRepository repository;

    @Override
    @Cacheable(value = "providers")
    public Map<String, String> findAll() {
        Map<String, String> providers = new HashMap<>();
        if (repository instanceof InMemoryClientRegistrationRepository inMemoryRepository) {
            for (ClientRegistration registration: inMemoryRepository) {
                providers.put(
                        registration.getRegistrationId(),
                        AccountSecurityFilterChain.OAUTH2_URL_TEMPLATE.formatted(registration.getRegistrationId())
                );
            }
        }
        return providers;
    }
}
