package com.specialist.auth.core.oauth2;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.util.UUID;

@RedisHash(value = "oauth2:users")
@Data
@NoArgsConstructor
@ToString(exclude = "ttl")
public class OAuthUserEntity {
    private UUID accountId;
    @Id
    private String email;
    private String lastName;
    private String firstName;
    private String secondName;
    private String avatarUrl;
    @TimeToLive
    private Duration ttl;

    public OAuthUserEntity(UUID accountId, String email) {
        this.accountId = accountId;
        this.email = email;
    }
}