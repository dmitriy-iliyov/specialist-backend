package com.specialist.core.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    @Primary
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        return new LettuceConnectionFactory(config);
    }

    @Bean
    @Primary
    public RedisCacheConfiguration defaultRedisCacheConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(),
                ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.PROPERTY
        );
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.
                        SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(mapper)));
    }

    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory  redisConnectionFactory, RedisCacheConfiguration defaultConfig) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .disableCreateOnMissingCache()
                .withCacheConfiguration("specialists:types:exists", defaultConfig)
                .withCacheConfiguration("specialists:types:suggested:id", defaultConfig)
                .withCacheConfiguration("specialists:types:suggested", defaultConfig)
                .withCacheConfiguration("specialists:types:approved:all", defaultConfig)
                .withCacheConfiguration("specialists", defaultConfig.entryTtl(Duration.ofSeconds(120)))
                .withCacheConfiguration("specialists:short-info", defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration("specialists:all", defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration("specialists:count:total", defaultConfig.entryTtl(Duration.ofSeconds(1200)))
                .withCacheConfiguration("specialists:filter", defaultConfig.entryTtl(Duration.ofSeconds(180)))
                .withCacheConfiguration("specialists:count:filter", defaultConfig.entryTtl(Duration.ofSeconds(600)))
                .withCacheConfiguration("specialists:created:count:total", defaultConfig.entryTtl(Duration.ofSeconds(1200)))
                .withCacheConfiguration("specialists:bookmarks:count:total", defaultConfig.entryTtl(Duration.ofSeconds(600)))
                .withCacheConfiguration("specialists:created:count:filter", defaultConfig.entryTtl(Duration.ofSeconds(1200)))
                .withCacheConfiguration("specialists:bookmarks:id_pairs", defaultConfig.entryTtl(Duration.ofSeconds(600)))

                .withCacheConfiguration("reviews", defaultConfig.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("accounts:emails", defaultConfig.entryTtl(Duration.ofSeconds(120)))
                .withCacheConfiguration("accounts:roles:id", defaultConfig)
                .withCacheConfiguration("accounts:authorities:id", defaultConfig)

                .withCacheConfiguration("providers", defaultConfig)
                .withCacheConfiguration("providers:paths", defaultConfig)

                .withCacheConfiguration("refresh-tokens:active", defaultConfig)

                .withCacheConfiguration("users:events:creator-rating-update:processed", defaultConfig.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration("profiles:emails", defaultConfig)

                .withCacheConfiguration(ScheduleCacheConfig.APPOINTMENT_DURATION_CACHE, defaultConfig)
                .withCacheConfiguration(ScheduleCacheConfig.APPOINTMENT_DURATION_MAP_CACHE, defaultConfig)
                .withCacheConfiguration(ScheduleCacheConfig.APPOINTMENTS_CACHE, defaultConfig)
                .withCacheConfiguration(ScheduleCacheConfig.APPOINTMENTS_KEY_TEMPLATE, defaultConfig)
                .withCacheConfiguration(ScheduleCacheConfig.APPOINTMENTS_BY_DATE_AND_STATUS_CACHE, defaultConfig)
                .withCacheConfiguration(ScheduleCacheConfig.APPOINTMENTS_BY_DATE_INTERVAL_CACHE, defaultConfig)
                .withCacheConfiguration(ScheduleCacheConfig.INTERVALS_BY_DATE_CACHE, defaultConfig)
                .withCacheConfiguration(ScheduleCacheConfig.INTERVALS_BY_DATE_INTERVAL_CACHE, defaultConfig)
                .withCacheConfiguration(ScheduleCacheConfig.APPOINTMENT_CANCEL_TASK_EXISTS, defaultConfig)
                .withCacheConfiguration("outbox-consumed-events", defaultConfig.entryTtl(Duration.ofHours(12)))
                .build();
    }
}