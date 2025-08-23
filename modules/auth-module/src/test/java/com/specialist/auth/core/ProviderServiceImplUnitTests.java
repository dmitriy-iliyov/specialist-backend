package com.specialist.auth.core;

import com.specialist.auth.core.oauth2.provider.ProviderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ProviderServiceImplUnitTests {

    @Test
    @DisplayName("UT: findAll() should return empty map if repository is not InMemoryClientRegistrationRepository")
    void findAll_withNonInMemoryRepository_shouldReturnEmptyMap() {
        ClientRegistrationRepository dummyRepository = null;
        ProviderServiceImpl service = new ProviderServiceImpl(dummyRepository);

        List<String> result = service.findAll();

        assertTrue(result.isEmpty(), "Expected empty map if repository is not InMemoryClientRegistrationRepository");
    }

    @Test
    @DisplayName("UT: findAll() should return map with registration IDs and OAuth2 URLs when using InMemoryClientRegistrationRepository")
    void findAll_withInMemoryRepository_shouldReturnProvidersMap() {
        ClientRegistration registration1 = ClientRegistration.withRegistrationId("google")
                .clientId("clientId")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://oauth2.googleapis.com/token")
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .clientSecret("secret")
                .scope("openid", "profile", "email")
                .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();

        ClientRegistration registration2 = ClientRegistration.withRegistrationId("github")
                .clientId("clientId")
                .authorizationUri("https://github.com/login/oauth/authorize")
                .tokenUri("https://github.com/login/oauth/access_token")
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .clientSecret("secret")
                .scope("read:user")
                .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();

        InMemoryClientRegistrationRepository repository = new InMemoryClientRegistrationRepository(registration1, registration2);
        ProviderServiceImpl service = new ProviderServiceImpl(repository);

        List<String> result = service.findAll();

        assertEquals(2, result.size(), "Expected two providers in the map");
        assertTrue(result.contains("google"));
        assertTrue(result.contains("github"));
    }
}
