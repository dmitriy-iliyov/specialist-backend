//package com.specialist.auth.core.oauth2;
//
//import com.specialist.auth.core.oauth2.mappers.OAuth2UserMapper;
//import com.specialist.auth.core.oauth2.models.OAuth2UserEntity;
//import com.specialist.auth.core.oauth2.models.Provider;
//import com.specialist.auth.core.oauth2.services.DefaultOAuth2UserServiceDecorator;
//import com.specialist.auth.domain.account.models.dtos.OAuth2AccountCreateDto;
//import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
//import com.specialist.auth.domain.account.services.AccountService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class DefaultOAuth2UserServiceDecoratorUnitTests {
//
//    @Mock
//    AccountService accountService;
//
//    @Mock
//    OAuth2UserMapper mapper;
//
//    @Mock
//    DefaultOAuth2UserServiceDecorator self;
//
//    @Mock
//    OAuth2UserRepository repository;
//
//    @Mock
//    OAuth2UserRequest userRequest;
//
//    @Mock
//    ClientRegistration clientRegistration;
//
//    @Mock
//    OAuth2UserEntity oAuthUserEntity;
//
//    DefaultOAuth2UserServiceDecorator service;
//
//    private static final String TEST_EMAIL = "test@example.com";
//    private static final UUID TEST_ACCOUNT_ID = UUID.randomUUID();
//
//    @BeforeEach
//    void setUp() {
//        service = new DefaultOAuth2UserServiceDecorator(accountService, mapper, self, repository);
//        service.TTL = 3600L;
//    }
//
//    @Test
//    @DisplayName("UT: saveIfNonExists() with new user should create account and save OAuth entity")
//    void saveIfNonExists_newUser_shouldCreateAccountAndSaveOAuthEntity() {
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("email", TEST_EMAIL);
//        attributes.put("name", "Test User");
//
//        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
//                null, attributes, "email"
//        );
//
//        ShortAccountResponseDto savedAccount = new ShortAccountResponseDto(
//                TEST_ACCOUNT_ID, TEST_EMAIL, LocalDateTime.now()
//        );
//
//        when(accountService.existsByEmail(TEST_EMAIL)).thenReturn(false);
//        when(accountService.save(any(OAuth2AccountCreateDto.class))).thenReturn(savedAccount);
//        when(mapper.toEntity(Provider.GOOGLE, TEST_ACCOUNT_ID, oAuth2User)).thenReturn(oAuthUserEntity);
//
//        service.saveIfNonExists(Provider.GOOGLE, oAuth2User);
//
//        verify(accountService).existsByEmail(TEST_EMAIL);
//        verify(accountService).save(any(OAuth2AccountCreateDto.class));
//        verify(mapper).toEntity(Provider.GOOGLE, TEST_ACCOUNT_ID, oAuth2User);
//        verify(oAuthUserEntity).setTtl(Duration.ofSeconds(3600L));
//        verify(repository).save(oAuthUserEntity);
//        verifyNoMoreInteractions(accountService, mapper, oAuthUserEntity, repository);
//    }
//
//    @Test
//    @DisplayName("UT: saveIfNonExists() with existing user should not create account or save OAuth entity")
//    void saveIfNonExists_existingUser_shouldNotCreateAccountOrSaveOAuthEntity() {
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("email", TEST_EMAIL);
//        attributes.put("name", "Existing User");
//
//        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
//                null, attributes, "email"
//        );
//
//        when(accountService.existsByEmail(TEST_EMAIL)).thenReturn(true);
//
//        service.saveIfNonExists(Provider.FACEBOOK, oAuth2User);
//
//        verify(accountService).existsByEmail(TEST_EMAIL);
//        verifyNoInteractions(mapper, repository);
//        verifyNoMoreInteractions(accountService);
//    }
//
//    @Test
//    @DisplayName("UT: saveIfNonExists() should handle null email gracefully")
//    void saveIfNonExists_nullEmail_shouldHandleGracefully() {
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("name", "User Without Email");
//
//        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
//                null, attributes, "name"
//        );
//
//        when(accountService.existsByEmail(null)).thenReturn(false);
//
//        service.saveIfNonExists(Provider.GOOGLE, oAuth2User);
//
//        verify(accountService).existsByEmail(null);
//        verifyNoMoreInteractions(accountService);
//        verifyNoInteractions(mapper, repository);
//    }
//
//    @Test
//    @DisplayName("UT: saveIfNonExists() should set correct TTL duration on OAuth entity")
//    void saveIfNonExists_newUser_shouldSetCorrectTTLDuration() {
//        service.TTL = 7200L;
//
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("email", TEST_EMAIL);
//
//        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
//                null, attributes, "email"
//        );
//
//        ShortAccountResponseDto savedAccount = new ShortAccountResponseDto(
//                TEST_ACCOUNT_ID, TEST_EMAIL, LocalDateTime.now()
//        );
//
//        when(accountService.existsByEmail(TEST_EMAIL)).thenReturn(false);
//        when(accountService.save(any(OAuth2AccountCreateDto.class))).thenReturn(savedAccount);
//        when(mapper.toEntity(Provider.APPLE, TEST_ACCOUNT_ID, oAuth2User)).thenReturn(oAuthUserEntity);
//
//        service.saveIfNonExists(Provider.APPLE, oAuth2User);
//
//        verify(oAuthUserEntity).setTtl(Duration.ofSeconds(7200L));
//        verify(repository).save(oAuthUserEntity);
//        verifyNoMoreInteractions(oAuthUserEntity, repository);
//    }
//
//    @Test
//    @DisplayName("UT: saveIfNonExists() should create OAuth2AccountCreateDto with correct parameters for Apple")
//    void saveIfNonExists_appleProvider_shouldCreateCorrectDTO() {
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("email", "apple@example.com");
//        attributes.put("sub", "appleuser");
//
//        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
//                null, attributes, "email"
//        );
//
//        ShortAccountResponseDto savedAccount = new ShortAccountResponseDto(
//                TEST_ACCOUNT_ID, "apple@example.com", LocalDateTime.now()
//        );
//
//        when(accountService.existsByEmail("apple@example.com")).thenReturn(false);
//        when(accountService.save(any(OAuth2AccountCreateDto.class))).thenReturn(savedAccount);
//        when(mapper.toEntity(Provider.APPLE, TEST_ACCOUNT_ID, oAuth2User)).thenReturn(oAuthUserEntity);
//
//        service.saveIfNonExists(Provider.APPLE, oAuth2User);
//
//        verify(accountService).save(any(OAuth2AccountCreateDto.class));
//        verifyNoMoreInteractions(accountService);
//    }
//
//    @Test
//    @DisplayName("UT: saveIfNonExists() should pass correct parameters to mapper.toEntity()")
//    void saveIfNonExists_newUser_shouldPassCorrectParametersToMapper() {
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("email", TEST_EMAIL);
//        attributes.put("sub", "123456789");
//
//        DefaultOAuth2User oAuth2User = new DefaultOAuth2User(
//                null, attributes, "email"
//        );
//
//        ShortAccountResponseDto savedAccount = new ShortAccountResponseDto(
//                TEST_ACCOUNT_ID, TEST_EMAIL, LocalDateTime.now()
//        );
//
//        when(accountService.existsByEmail(TEST_EMAIL)).thenReturn(false);
//        when(accountService.save(any(OAuth2AccountCreateDto.class))).thenReturn(savedAccount);
//        when(mapper.toEntity(Provider.GOOGLE, TEST_ACCOUNT_ID, oAuth2User)).thenReturn(oAuthUserEntity);
//
//        service.saveIfNonExists(Provider.GOOGLE, oAuth2User);
//
//        verify(mapper).toEntity(Provider.GOOGLE, TEST_ACCOUNT_ID, oAuth2User);
//        verifyNoMoreInteractions(mapper);
//    }
//}