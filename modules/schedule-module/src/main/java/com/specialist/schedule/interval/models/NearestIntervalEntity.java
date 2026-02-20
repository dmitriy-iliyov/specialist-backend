package com.specialist.schedule.interval.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@RedisHash(value = "interval:nearest")
@Data
public class NearestIntervalEntity {

    @Id
    @JsonProperty("specialist_id")
    private UUID specialistId;

    private Long id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime start;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @TimeToLive
    private long ttlSeconds;

    @JsonCreator
    public NearestIntervalEntity(@JsonProperty("id")
                                 Long id,

                                 @JsonProperty("specialist_id")
                                 UUID specialistId,

                                 @JsonProperty("start")
                                 @JsonFormat(pattern = "HH:mm")
                                 LocalTime start,

                                 @JsonProperty("id")
                                 @JsonFormat(pattern = "yyyy-MM-dd")
                                 LocalDate date) {
        this.id = id;
        this.specialistId = specialistId;
        this.start = start;
        this.date = date;
    }
}
