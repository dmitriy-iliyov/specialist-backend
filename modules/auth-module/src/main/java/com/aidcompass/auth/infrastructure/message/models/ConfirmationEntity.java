package com.aidcompass.auth.infrastructure.message.models;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;

@RedisHash("accounts:conf")
public record ConfirmationEntity(
        @Id
        String code,
        String email,
        @TimeToLive
        Duration ttl
) { }