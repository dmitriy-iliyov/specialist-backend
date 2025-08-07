package com.specialist.auth.infrastructure.message.models;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;

@RedisHash(value = "accounts:pass-recovery")
public record PasswordRecoveryEntity(
        @Id
        String code,
        String email,
        @TimeToLive
        Duration ttl
) { }
