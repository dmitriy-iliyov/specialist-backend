package com.specialist.specialistdirectory.domain.specialist.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ActionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@RedisHash("specialists:action")
@Data
@NoArgsConstructor
public class ActionEntity {

    @Id
    private String code;

    private ActionType type;

    @JsonProperty("specialist_id")
    private UUID specialistId;

    @JsonProperty("account_id")
    private UUID accountId;

    @TimeToLive
    private Long ttl;

    @JsonCreator
    public ActionEntity(
            @JsonProperty("code") String code,
            @JsonProperty("type") ActionType type,
            @JsonProperty("specialist_id") UUID specialistId,
            @JsonProperty("account_id") UUID accountId,
            @JsonProperty("ttl") Long ttl
    ) {
        this.code = code;
        this.type = type;
        this.specialistId = specialistId;
        this.accountId = accountId;
        this.ttl = ttl;
    }

    public ActionEntity(ActionType type, UUID specialistId, UUID accountId, Long ttl) {
        this.type = type;
        this.specialistId = specialistId;
        this.accountId = accountId;
        this.ttl = ttl;
    }
}
