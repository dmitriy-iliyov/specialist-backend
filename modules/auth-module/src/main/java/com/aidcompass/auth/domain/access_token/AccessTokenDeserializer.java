package com.aidcompass.auth.domain.access_token;

import com.aidcompass.auth.domain.access_token.models.AccessToken;

public interface AccessTokenDeserializer {
    AccessToken deserialize(String rawToken);
}
