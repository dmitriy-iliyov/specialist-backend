package com.aidcompass.auth.infrastructure.message.models;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.util.UUID;

@RedisHash("accounts:conf")
public record ConfirmationEntity(
        @Id
        String code,
        UUID accountId,
        @TimeToLive
        Duration ttl
) { }