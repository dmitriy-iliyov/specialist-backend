package com.specialist.auth.infrastructure.message.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "accounts:pass-recovery")
public record PasswordRecoveryEntity(
        @Id
        String code,
        String email,
        @TimeToLive
        Long ttl
) { }
