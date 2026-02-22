package com.specialist.auth.core.web.oauth2.services;

import com.specialist.auth.core.web.oauth2.OAuth2UserRepository;
import com.specialist.auth.core.web.oauth2.mappers.OAuth2UserMapper;
import com.specialist.auth.core.web.oauth2.models.OAuth2UserEntity;
import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.services.AccountPersistFacade;
import com.specialist.auth.domain.account.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuth2AccountPersistServiceImplUnitTests {

    @Mock
    private AccountService service;
    @Mock
    private AccountPersistFacade persistFacade;
    @Mock
    private OAuth2UserMapper mapper;
    @Mock
    private OAuth2UserRepository repository;

    @InjectMocks
    private OAuth2AccountPersistServiceImpl persistService;

    @BeforeEach
    void setUp() {
        persistService.TTL = 3600L;
    }

    @Test
    @DisplayName("UT: saveIfNonExists() when account not exists should create account and save oauth user")
    void saveIfNonExists_whenAccountNotExists_shouldCreateAndSave() {
        Provider provider = Provider.GOOGLE;
        OAuth2User oAuth2User = mock(OAuth2User.class);
        String email = "test@example.com";
        UUID accountId = UUID.randomUUID();
        OAuth2UserEntity entity = new OAuth2UserEntity();

        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(service.existsByEmail(email)).thenReturn(false);
        when(persistFacade.save(email, provider)).thenReturn(new ShortAccountResponseDto(accountId, email));
        when(mapper.toEntity(provider, accountId, oAuth2User)).thenReturn(entity);

        persistService.saveIfNonExists(provider, oAuth2User);

        verify(service).existsByEmail(email);
        verify(persistFacade).save(email, provider);
        verify(mapper).toEntity(provider, accountId, oAuth2User);
        verify(repository).save(entity);
        verifyNoMoreInteractions(service, persistFacade, mapper, repository);
    }

    @Test
    @DisplayName("UT: saveIfNonExists() when account exists should do nothing")
    void saveIfNonExists_whenAccountExists_shouldDoNothing() {
        Provider provider = Provider.GOOGLE;
        OAuth2User oAuth2User = mock(OAuth2User.class);
        String email = "test@example.com";

        when(oAuth2User.getAttribute("email")).thenReturn(email);
        when(service.existsByEmail(email)).thenReturn(true);

        persistService.saveIfNonExists(provider, oAuth2User);

        verify(service).existsByEmail(email);
        verifyNoInteractions(persistFacade, mapper, repository);
    }

    @Test
    @DisplayName("UT: saveIfNonExists() when email is null should do nothing")
    void saveIfNonExists_whenEmailNull_shouldDoNothing() {
        Provider provider = Provider.GOOGLE;
        OAuth2User oAuth2User = mock(OAuth2User.class);

        when(oAuth2User.getAttribute("email")).thenReturn(null);

        persistService.saveIfNonExists(provider, oAuth2User);

        verifyNoInteractions(service, persistFacade, mapper, repository);
    }
}
