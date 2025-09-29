package com.specialist.auth.core.oauth2.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "oauth2:initial-request")
public record OAuth2InitialRequestEntity(
        @Id
        String state,
        String redirectUri,
        @TimeToLive
        Long ttl
) { }
