package com.specialist.auth.ut.core.web.oauth2.services;

import com.specialist.auth.core.web.oauth2.services.ProviderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProviderServiceImplUnitTests {

    @Mock
    private InMemoryClientRegistrationRepository repository;

    @InjectMocks
    private ProviderServiceImpl service;

    @Test
    @DisplayName("UT: findAll() should return list of registration ids")
    void findAll_shouldReturnIds() {
        ClientRegistration registration = ClientRegistration.withRegistrationId("google")
                .clientId("id")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("uri")
                .redirectUri("uri")
                .tokenUri("uri")
                .build();

        when(repository.iterator()).thenReturn(List.of(registration).iterator());

        List<String> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("google", result.get(0));
    }

    @Test
    @DisplayName("UT: findAllPaths() should return map of ids and auth urls")
    void findAllPaths_shouldReturnMap() {
        ClientRegistration registration = ClientRegistration.withRegistrationId("google")
                .clientId("id")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("uri")
                .redirectUri("uri")
                .tokenUri("uri")
                .build();

        when(repository.iterator()).thenReturn(List.of(registration).iterator());

        Map<String, String> result = service.findAllPaths();

        assertEquals(1, result.size());
        assertTrue(result.containsKey("google"));
        assertTrue(result.get("google").contains("google"));
    }
}
