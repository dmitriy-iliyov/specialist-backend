package com.aidcompass.core.general;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class GlobalRedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;


    public static final String APPOINTMENT_DURATION_CACHE = "appointment:duration";
    public static final String APPOINTMENT_DURATION_MAP_CACHE = "appointment:duration:map";
    public static final String APPOINTMENTS_CACHE = "appointments";
    public static final String APPOINTMENTS_KEY_TEMPLATE = "appointments::%s";
    public final static String APPOINTMENTS_BY_DATE_AND_STATUS_CACHE = "appointments:date";
    public final static String APPOINTMENTS_BY_DATE_INTERVAL_CACHE = "appointments:date_interval";
    public final static String CONF_TOKEN_KEY_TEMPLATE = "tkn:conf:";
    public final static String INTERVALS_BY_DATE_CACHE = "intervals:date";
    public final static String INTERVALS_BY_DATE_INTERVAL_CACHE = "intervals:date_interval";


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
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.
                        SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(mapper)));
    }

    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory  redisConnectionFactory,
                                     @Qualifier("defaultRedisCacheConfiguration") RedisCacheConfiguration defaultConfig) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .disableCreateOnMissingCache()

                .withCacheConfiguration("users:exists", defaultConfig.entryTtl(Duration.ofSeconds(3600)))

                .withCacheConfiguration("doctors:public", defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration("doctors:public:full", defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration("doctors:spec", defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration("doctors:name", defaultConfig.entryTtl(Duration.ofSeconds(420)))
                .withCacheConfiguration("doctors:gender", defaultConfig.entryTtl(Duration.ofSeconds(360)))
                .withCacheConfiguration("doctors:approve", defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration("doctors:count", defaultConfig.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("jurists:public", defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration("jurists:public:full", defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration("jurists:spec", defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration("jurists:name", defaultConfig.entryTtl(Duration.ofSeconds(420)))
                .withCacheConfiguration("jurists:gender", defaultConfig.entryTtl(Duration.ofSeconds(360)))
                .withCacheConfiguration("jurists:approve", defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration("jurists:count", defaultConfig.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("customers:private", defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration("customers:count", defaultConfig.entryTtl(Duration.ofSeconds(300)))

                .withCacheConfiguration("contact_types", defaultConfig)
                .withCacheConfiguration("exists", defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration("public_contacts", defaultConfig.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration("primary_contacts", defaultConfig.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration("contacts:progress", defaultConfig.entryTtl(Duration.ofSeconds(3600)))

                .withCacheConfiguration(APPOINTMENT_DURATION_CACHE, defaultConfig)
                .withCacheConfiguration(APPOINTMENT_DURATION_MAP_CACHE, defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration(APPOINTMENTS_CACHE, defaultConfig.entryTtl(Duration.ofSeconds(3600)))
                .withCacheConfiguration(INTERVALS_BY_DATE_CACHE, defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration(INTERVALS_BY_DATE_INTERVAL_CACHE, defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration(APPOINTMENTS_BY_DATE_AND_STATUS_CACHE, defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration(APPOINTMENTS_BY_DATE_INTERVAL_CACHE, defaultConfig.entryTtl(Duration.ofSeconds(7200)))

                .withCacheConfiguration("avatars:url", defaultConfig.entryTtl(Duration.ofSeconds(7200)))
                .withCacheConfiguration("avatars:url:map", defaultConfig.entryTtl(Duration.ofSeconds(120)))

                .withCacheConfiguration("specialists:types:suggested:id", defaultConfig)
                .withCacheConfiguration("specialists:types:suggested", defaultConfig)
                .withCacheConfiguration("specialists:types:approved:all", defaultConfig)
                .withCacheConfiguration("specialists", defaultConfig.entryTtl(Duration.ofSeconds(120)))
                .withCacheConfiguration("specialists:creator_id", defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration("specialists:all", defaultConfig.entryTtl(Duration.ofSeconds(600)))
                .withCacheConfiguration("specialists:count:total", defaultConfig.entryTtl(Duration.ofSeconds(1200)))
                .withCacheConfiguration("specialists:filter", defaultConfig.entryTtl(Duration.ofSeconds(300)))
                .withCacheConfiguration("specialists:count:filter", defaultConfig.entryTtl(Duration.ofSeconds(600)))
                .withCacheConfiguration("specialists:created:count:total", defaultConfig.entryTtl(Duration.ofSeconds(1200)))
                .withCacheConfiguration("specialists:bookmarks:count:total", defaultConfig.entryTtl(Duration.ofSeconds(600)))
                .withCacheConfiguration("specialists:created:count:filter", defaultConfig.entryTtl(Duration.ofSeconds(1200)))
                .withCacheConfiguration("specialists:bookmarks:specialist_ids", defaultConfig.entryTtl(Duration.ofSeconds(600)))
                .build();
    }
}