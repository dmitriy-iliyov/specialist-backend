package com.specialist.auth.domain.refresh_token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class RefreshTokenCleanUpScheduler {

    private final Integer batchSize;
    private final RefreshTokenService service;

    public RefreshTokenCleanUpScheduler(@Value("${api.refresh-token.clean-up.batch-size}") Integer batchSize,
                                        RefreshTokenService service) {
        this.batchSize = batchSize;
        this.service = service;
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void cleanUp() {
        try {
            service.deleteBatchByExpiredAtThreshold(Instant.now(), batchSize);
        } catch (Exception e) {
            log.error("Error when cleanup expired refresh token", e);
        }
    }
}
