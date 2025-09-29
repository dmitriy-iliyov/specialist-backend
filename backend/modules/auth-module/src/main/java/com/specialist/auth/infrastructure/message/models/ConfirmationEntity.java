package com.specialist.auth.infrastructure.message.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("accounts:conf")
public record ConfirmationEntity(
        @Id
        String code,
        String email,
        @TimeToLive
        Long ttl
) { }