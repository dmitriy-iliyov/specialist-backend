//package com.aidcompass.recovery;
//
//import com.aidcompass.clients.AuthService;
//import com.aidcompass.exceptions.models.InvalidPasswordRecoveryTokenException;
//import com.aidcompass.message.MessageService;
//import com.aidcompass.message.models.MessageDto;
//import io.jsonwebtoken.lang.Strings;
//import jakarta.mail.MessagingException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.Base64;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Testcontainers
//public class EmailPasswordRecoveryServiceIntegrationTests {
//
//    @Autowired
//    EmailPasswordRecoveryService recoveryService;
//
//    @Autowired
//    RedisPasswordRecoveryRepository redisPasswordRecoveryRepository;
//
//    @MockitoBean
//    MessageService messageService;
//
//    @MockitoBean
//    AuthService authService;
//
//    @Container
//    static GenericContainer<?> redis = new GenericContainer<>("redis:7.0.5")
//            .withExposedPorts(6379);
//
//    @DynamicPropertySource
//    static void redisProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.redis.host", redis::getHost);
//        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
//    }
//
//    @Test
//    @DisplayName("IT: sendRecoveryMessage() should persist code in Redis")
//    void sendRecoveryMessage_shouldStoreTokenInRedis() throws Exception {
//        String testEmail = "test@domain.com";
//        recoveryService.sendRecoveryMessage(testEmail);
//
//        ArgumentCaptor<MessageDto> captor = ArgumentCaptor.forClass(MessageDto.class);
//        verify(messageService).sendMessage(captor.capture());
//
//        MessageDto message = captor.getValue();
//        assertEquals(testEmail, message.recipient());
//
//        UUID code = UUID.fromString(Strings.ascii(Base64.getUrlDecoder().decode(message.text().split("code=")[1].getBytes())));
//        Optional<String> emailInRedis = redisPasswordRecoveryRepository.findAndDeleteByToken(code);
//
//        assertTrue(emailInRedis.isPresent());
//        assertEquals(testEmail, emailInRedis.get());
//
//        verify(messageService, times(1)).sendMessage(message);
//        verifyNoMoreInteractions(messageService);
//    }
//
//    @Test
//    @DisplayName("IT: sendRecoveryMessage() shouldn't persist code if MessagingException is thrown")
//    void sendRecoveryMessage_shouldNotStoreTokenIfException() throws Exception {
//        String testEmail = "fail@domain.com";
//        doThrow(new MessagingException()).when(messageService).sendMessage(any());
//
//        assertThrows(MessagingException.class, () -> recoveryService.sendRecoveryMessage(testEmail));
//
//        ArgumentCaptor<MessageDto> captor = ArgumentCaptor.forClass(MessageDto.class);
//        verify(messageService).sendMessage(captor.capture());
//
//        MessageDto msg = captor.getValue();
//        UUID code = UUID.fromString(Strings.ascii(Base64.getUrlDecoder().decode(msg.text().split("code=")[1].getBytes())));
//        Optional<String> result = redisPasswordRecoveryRepository.findAndDeleteByToken(code);
//
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    @DisplayName("IT: recoverPassword() should succeed with valid code")
//    void recoverPassword_shouldRecover_whenValidTokenProvided() throws Exception {
//        String email = "user@domain.com";
//        String newPassword = "new_secure_pass";
//
//        recoveryService.sendRecoveryMessage(email);
//
//        ArgumentCaptor<MessageDto> captor = ArgumentCaptor.forClass(MessageDto.class);
//        verify(messageService).sendMessage(captor.capture());
//
//        String encodedToken = captor.getValue().text().split("code=")[1];
//        recoveryService.recoverPassword(encodedToken, newPassword);
//
//        verify(authService).recoverPassword(email, newPassword);
//    }
//
//    @Test
//    @DisplayName("IT: recoverPassword() should throw if code not found")
//    void recoverPassword_shouldThrow_whenTokenInvalid() {
//        UUID randomToken = UUID.randomUUID();
//        String encoded = Base64.getUrlEncoder().encodeToString(randomToken.toString().getBytes());
//
//        assertThrows(InvalidPasswordRecoveryTokenException.class, () -> recoveryService.recoverPassword(encoded, "123"));
//    }
//
//    @Test
//    @DisplayName("IT: recoverPassword() should throw if code malformed")
//    void recoverPassword_shouldThrow_whenTokenMalformed() {
//        String brokenToken = "non-base64-code";
//
//        assertThrows(IllegalArgumentException.class, () -> recoveryService.recoverPassword(brokenToken, "123"));
//    }
//
//    @Test
//    @DisplayName("IT: code should expire after TTL")
//    void tokenShouldExpireAfterTTL() throws Exception {
//        String email = "expire@test.com";
//        recoveryService.sendRecoveryMessage(email);
//
//        ArgumentCaptor<MessageDto> captor = ArgumentCaptor.forClass(MessageDto.class);
//        verify(messageService).sendMessage(captor.capture());
//
//        String code = captor.getValue().text().split("code=")[1];
//
//        Thread.sleep(10010);
//
//        assertThrows(InvalidPasswordRecoveryTokenException.class,
//                () -> recoveryService.recoverPassword(code, "irrelevant"));
//    }
//}