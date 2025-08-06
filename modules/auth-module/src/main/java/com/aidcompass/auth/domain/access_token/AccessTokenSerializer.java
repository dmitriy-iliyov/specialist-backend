package com.aidcompass.auth.domain.access_token;

import com.aidcompass.auth.domain.access_token.models.AccessToken;

public interface AccessTokenSerializer {
    String serialize(AccessToken accessToken);
}
