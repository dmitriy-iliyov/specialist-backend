package com.specialist.auth.core.oauth2;

import com.specialist.auth.domain.account.models.dtos.OAuth2AccountCreateDto;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.domain.auth_provider.Provider;
import com.specialist.auth.domain.authority.AuthorityServiceImpl;
import com.specialist.auth.domain.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultOAuth2UserServiceDecorator extends DefaultOAuth2UserService {

    private final AccountService service;
    private final OAuth2UserMapper mapper;
    private final DefaultOAuth2UserServiceDecorator self;
    private final OAuth2UserRepository repository;

    @Value("${api.oauth-user.ttl}")
    public Long TTL;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) super.loadUser(userRequest);
        Provider provider = Provider.fromName(userRequest.getClientRegistration().getRegistrationId());
        self.saveIfNonExists(provider, oAuth2User);
        return oAuth2User;
    }

    @Transactional
    public void saveIfNonExists(Provider provider, OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        if (!service.existsByEmail(email)) {
            OAuth2AccountCreateDto createDto = new OAuth2AccountCreateDto(
                    email,
                    provider,
                    Role.ROLE_USER,
                    AuthorityServiceImpl.DEFAULT_USER_AUTHORITIES
            );
            UUID accountId = service.save(createDto).id();
            OAuthUserEntity entity = mapper.toEntity(provider, accountId, oAuth2User);
            entity.setTtl(Duration.ofSeconds(TTL));
            repository.save(entity);
        }
    }
}
