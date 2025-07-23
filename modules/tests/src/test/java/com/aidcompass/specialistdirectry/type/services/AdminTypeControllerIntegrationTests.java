package com.aidcompass.specialistdirectry.type.services;


import com.aidcompass.AidCompassBackend;
import com.aidcompass.specialistdirectory.domain.type.services.interfaces.ApproveTypeOrchestrator;
import com.aidcompass.specialistdirectory.domain.type.services.interfaces.TypeOrchestrator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = AidCompassBackend.class)
@ActiveProfiles("test")
@Testcontainers
public class AdminTypeControllerIntegrationTests {

    @Autowired
    TypeOrchestrator orchestrator;

    @Autowired
    ApproveTypeOrchestrator approveOrchestrator;

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7.0.5")
            .withExposedPorts(6379);

    @Container
    static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("specialists-test-db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
        registry.add("spring.datasource.url", postgresql::getJdbcUrl);
        registry.add("spring.datasource.username", postgresql::getUsername);
        registry.add("spring.datasource.password", postgresql::getPassword);
    }


    @Test
    @DisplayName("IT create() should persist and return 200.")
    public void create_whenValidDto_shouldReturn200() {

    }

    @Test
    @DisplayName("IT create() should return 400.")
    public void create_whenInvalidTypeDto_shouldReturn400() {

    }

    @Test
    @DisplayName("IT create() should return 400.")
    public void create_whenInvalidTranslateDtoList_shouldReturn400() {

    }

    @Test
    @DisplayName("IT create() should return 400.")
    public void create_whenInvalidRequest2_shouldReturn400() {

    }
}
