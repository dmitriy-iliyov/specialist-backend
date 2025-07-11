package com.aidcompass.core.security.domain.user.models;

import com.aidcompass.core.general.utils.uuid.UuidFactory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@RedisHash(value = "user:uncf", timeToLive = 86400)
public class UnconfirmedUserEntity {

    @Id
    private String email;
    private UUID id;
    private String password;


    @Deprecated
    public UnconfirmedUserEntity() {
        this.id = UuidFactory.generate();
    }

    @JsonCreator
    public UnconfirmedUserEntity(@JsonProperty("id") UUID id,
                                 @JsonProperty("email") String email,
                                 @JsonProperty("password") String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
