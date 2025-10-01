package com.specialist.auth.core.rate_limit;

import io.lettuce.core.RedisException;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisRateLimitRepository implements RateLimitRepository {

    private final StatefulRedisConnection<String, String> statefulConnection;
    private final ScriptLoader scriptLoader;
    private final String TARGET_URL;
    private final String KEY_TEMPLATE;
    private final Long MAX_ATTEMPT_COUNT;
    private final Long OBSERVE_TIME;
    private final Long LOCK_TIME;

    public RedisRateLimitRepository(StatefulRedisConnection<String, String> statefulConnection,
                                    ScriptLoader scriptLoader, String targetUrl, String keyTemplate,
                                    Long maxAttemptCount, Long observeTime, Long lockTime) {
        this.statefulConnection = statefulConnection;
        this.scriptLoader = scriptLoader;
        this.TARGET_URL = targetUrl;
        this.KEY_TEMPLATE = keyTemplate + ":ip:%s";
        this.MAX_ATTEMPT_COUNT = maxAttemptCount;
        this.OBSERVE_TIME = observeTime;
        this.LOCK_TIME = lockTime;
        log.info("Create RedisRateLimitRepository.class with TARGET_URL: {}", TARGET_URL);
    }

    @Override
    public String getTargetUrl() {
        return TARGET_URL;
    }

    @Override
    public RateLimitStatus increment(String ip) {
        String [] keys = {KEY_TEMPLATE.formatted(ip), "blocked:" + KEY_TEMPLATE.formatted(ip)};
        try {
            Boolean result = statefulConnection.sync().evalsha(
                    scriptLoader.getScriptSha(), ScriptOutputType.BOOLEAN, keys,
                    String.valueOf(OBSERVE_TIME), String.valueOf(LOCK_TIME), String.valueOf(MAX_ATTEMPT_COUNT)
            );
            return RateLimitStatus.fromBoolean(result);
        } catch (RedisException e) {
            log.error("Redis exception when incrementing limit, ", e);
            if (e.getMessage() != null && e.getMessage().contains("NOSCRIPT")) {
                scriptLoader.loadScript();
            }
            throw e;
        } catch (Exception e) {
            log.error("Error when incrementing limit, ", e);
            throw e;
        }
    }
}
