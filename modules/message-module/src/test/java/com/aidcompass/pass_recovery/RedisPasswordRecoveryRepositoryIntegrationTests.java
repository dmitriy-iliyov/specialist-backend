//package com.aidcompass.recovery;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.Optional;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Testcontainers
//public class RedisPasswordRecoveryRepositoryIntegrationTests {
//
//    @Autowired
//    RedisPasswordRecoveryRepository redisPasswordRecoveryRepository;
//
//    @Autowired
//    RedisTemplate<String, String> redisTemplate;
//
//    private final String RECOVERY_TOKEN_KEY_TEMPLATE = "tkn:pass:recov:%s";
//
//    @Container
//    static GenericContainer<?> redis = new GenericContainer<>("redis:7.0.5")
//            .withExposedPorts(6379);
//
//    @DynamicPropertySource
//    static void overrideRedisProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.redis.host", redis::getHost);
//        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
//    }
//
//    @Test
//    @DisplayName("IT: save() should store code")
//    void save_shouldSaveToRedis() {
//        UUID code = UUID.randomUUID();
//        String email = "recover@test.com";
//
//        redisPasswordRecoveryRepository.save(code, email);
//
//        assertTrue(Boolean.TRUE.equals(
//                redisTemplate.hasKey(RECOVERY_TOKEN_KEY_TEMPLATE.formatted(code))
//        ));
//    }
//
//    @Test
//    @DisplayName("IT: findAndDeleteByToken() should retrieve and remove code")
//    void findAndDeleteByToken__whenExist_shouldReturnEmail() {
//        UUID code = UUID.randomUUID();
//        String email = "recover@test.com";
//
//        redisPasswordRecoveryRepository.save(code, email);
//
//        Optional<String> result = redisPasswordRecoveryRepository.findAndDeleteByToken(code);
//        assertTrue(result.isPresent());
//        assertEquals(email, result.get());
//
//        result = redisPasswordRecoveryRepository.findAndDeleteByToken(code);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    @DisplayName("IT: save() should store code with TTL")
//    void save_shouldStoreWithTTL() {
//        UUID code = UUID.randomUUID();
//        String email = "recover@test.com";
//
//        redisPasswordRecoveryRepository.save(code, email);
//
//        Long ttl = redisTemplate.getExpire(RECOVERY_TOKEN_KEY_TEMPLATE.formatted(code), TimeUnit.SECONDS);
//        assertNotNull(ttl);
//        assertTrue(ttl > 0, "TTL must be greater than 0");
//    }
//
//    @Test
//    @DisplayName("IT: findAndDeleteByToken() should return empty if code not exists")
//    void findAndDeleteByToken_whenNotExist_shouldReturnEmpty() {
//        UUID code = UUID.randomUUID();
//
//        Optional<String> result = redisPasswordRecoveryRepository.findAndDeleteByToken(code);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    @DisplayName("IT: Token should be removed after first access")
//    void tokenShouldBeRemovedAfterFirstAccess() {
//        UUID code = UUID.randomUUID();
//        String email = "recover@test.com";
//
//        redisPasswordRecoveryRepository.save(code, email);
//
//        Optional<String> first = redisPasswordRecoveryRepository.findAndDeleteByToken(code);
//        Optional<String> second = redisPasswordRecoveryRepository.findAndDeleteByToken(code);
//
//        assertTrue(first.isPresent());
//        assertEquals(email, first.get());
//        assertTrue(second.isEmpty());
//    }
//
//    @Test
//    @DisplayName("IT: Key should match expected naming convention")
//    void keyShouldMatchNamingConvention() {
//        UUID code = UUID.randomUUID();
//        String email = "recover@test.com";
//
//        redisPasswordRecoveryRepository.save(code, email);
//
//        String expectedKey = "tkn:pass:recov:" + code;
//        assertTrue(Boolean.TRUE.equals(redisTemplate.hasKey(expectedKey)));
//    }
//}