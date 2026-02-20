package com.specialist.auth.core.web.oauth2.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@RedisHash(value = "oauth2:users")
@Data
@NoArgsConstructor
public class OAuth2UserEntity {
    @Id
    private UUID accountId;
    private String lastName;
    private String firstName;
    private String secondName;
    private String avatarUrl;
    @TimeToLive
    private Long ttl;

    public OAuth2UserEntity(UUID accountId) {
        this.accountId = accountId;
    }
}