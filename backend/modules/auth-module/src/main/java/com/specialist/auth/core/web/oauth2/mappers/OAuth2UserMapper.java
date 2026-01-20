package com.specialist.auth.core.web.oauth2.mappers;

import com.specialist.auth.core.web.oauth2.models.OAuth2UserEntity;
import com.specialist.auth.core.web.oauth2.models.Provider;
import com.specialist.auth.exceptions.OAuth2UserAttributesNullException;
import com.specialist.auth.exceptions.OAuth2UserNullException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public final class OAuth2UserMapper {

    public OAuth2UserEntity toEntity(Provider provider, UUID accountId, OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new OAuth2UserNullException();
        } else if (oAuth2User.getAttributes() == null) {
            throw new OAuth2UserAttributesNullException();
        }
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuth2UserEntity entity = new OAuth2UserEntity(accountId);
        switch (provider) {
            case GOOGLE -> {
                entity.setFirstName((String) attributes.get("given_name"));
                entity.setLastName((String) attributes.get("family_name"));
                entity.setAvatarUrl((String) attributes.get("picture"));
            }
            case FACEBOOK -> {
                entity.setFirstName((String) attributes.get("first_name"));
                entity.setLastName((String) attributes.get("last_name"));
                Map<String, Object> pictureData = oAuth2User.getAttribute("picture");
                if (pictureData != null) {
                    Map<String, Object> data = (Map<String, Object>) pictureData.get("data");
                    if (data != null) {
                        entity.setAvatarUrl((String) data.get("url"));
                    }
                }
            }
            default -> {}
        }
        return entity;
    }
}
