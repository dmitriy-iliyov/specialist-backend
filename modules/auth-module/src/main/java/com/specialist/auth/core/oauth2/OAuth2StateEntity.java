package com.specialist.auth.core.oauth2;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "oauth2:state")
public record OAuth2StateEntity(
        @Id
        String state,
        @TimeToLive
        Long ttl
) { }
