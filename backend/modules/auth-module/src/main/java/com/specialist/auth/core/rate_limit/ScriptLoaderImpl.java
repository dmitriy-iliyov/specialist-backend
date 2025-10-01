package com.specialist.auth.core.rate_limit;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScriptLoaderImpl implements ScriptLoader {

    private final RedisConnectionFactory connectionFactory;
    private volatile String SCRIPT_SHA;

    @PostConstruct
    @Override
    public synchronized void loadScript() {
        try {
            SCRIPT_SHA = connectionFactory.getConnection()
                    .scriptingCommands()
                    .scriptLoad(ScriptHolder.SCRIPT.getBytes(StandardCharsets.UTF_8));
        } catch (RedisConnectionFailureException e) {
            log.error("Failed connect to Redis when loading script, ", e);
            throw e;
        } catch (Exception e) {
            log.error("Error when loading script to Redis, ", e);
            throw e;
        }
    }

    @Override
    public String getScriptSha() {
        return SCRIPT_SHA;
    }

    private static class ScriptHolder {
        private static final String SCRIPT = loadScript("rate_limit_script.lua");

        private static String loadScript(String filepath) {
            try (InputStream is = ScriptLoaderImpl.class.getClassLoader().getResourceAsStream(filepath)) {
                if (is == null) {
                    throw new FileNotFoundException(filepath + " not found in resources");
                }
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
