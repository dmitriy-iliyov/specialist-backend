//package com.aidcompass.confirmation;
//
//import com.aidcompass.confirmation.repositories.RedisConfirmationRepository;
//import com.aidcompass.confirmation.services.EmailConfirmationService;
//import com.aidcompass.exceptions.models.InvalidConfirmationTokenException;
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
//public class EmailConfirmationServiceIntegrationTests {
//
//    @Autowired
//    EmailConfirmationService emailConfirmationService;
//
//    @Autowired
//    RedisConfirmationRepository redisConfirmationRepository;
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
//    static void overrideRedisProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.redis.host", redis::getHost);
//        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
//    }
//
//    @Test
//    @DisplayName("IT: sendConfirmationMessage() should persist code in Redis")
//    void sendConfirmationMessage_shouldSaveTokenToRedis() throws Exception {
//        String testEmail = "test@gmail.com";
//        emailConfirmationService.sendConfirmationMessage(testEmail);
//
//        ArgumentCaptor<MessageDto> messageDtoCaptor = ArgumentCaptor.forClass(MessageDto.class);
//        verify(messageService).sendMessage(messageDtoCaptor.capture());
//
//        MessageDto messageDto = messageDtoCaptor.getValue();
//        assertEquals(testEmail, messageDto.recipient());
//
//        UUID code = UUID.fromString(Strings.ascii(Base64.getUrlDecoder().decode(messageDto.text().split("code=")[1].getBytes())));
//        Optional<String> email = redisConfirmationRepository.findAndDeleteByToken(code);
//        assertFalse(email.isEmpty());
//        assertEquals(testEmail, email.get());
//        verify(messageService, times(1)).sendMessage(messageDto);
//        verifyNoMoreInteractions(messageService);
//    }
//
//    @Test
//    @DisplayName("IT: sendConfirmationMessage() shouldn't persist code in Redis if MessagingException occurs")
//    void sendConfirmationMessage_whenMessageExceptionInvolved_shouldNotSaveTokenToRedis() throws Exception {
//        String testEmail = "test@gmail.com";
//        doThrow(new MessagingException()).when(messageService).sendMessage(any(MessageDto.class));
//
//        assertThrows(MessagingException.class, () -> emailConfirmationService.sendConfirmationMessage(testEmail));
//
//        ArgumentCaptor<MessageDto> messageDtoCaptor = ArgumentCaptor.forClass(MessageDto.class);
//        verify(messageService).sendMessage(messageDtoCaptor.capture());
//
//        MessageDto messageDto = messageDtoCaptor.getValue();
//        assertEquals(testEmail, messageDto.recipient());
//
//        UUID code = UUID.fromString(Strings.ascii(Base64.getUrlDecoder().decode(messageDto.text().split("code=")[1].getBytes())));
//        Optional<String> email = redisConfirmationRepository.findAndDeleteByToken(code);
//        assertTrue(email.isEmpty());
//        verify(messageService, times(1)).sendMessage(messageDto);
//        verifyNoMoreInteractions(messageService);
//    }
//
//    @Test
//    @DisplayName("IT: validateConfirmationToken() should confirm user if code is valid")
//    void validateConfirmationToken_shouldConfirmUser_whenTokenIsValid() throws Exception {
//        String email = "test@email.com";
//        emailConfirmationService.sendConfirmationMessage(email);
//
//        ArgumentCaptor<MessageDto> captor = ArgumentCaptor.forClass(MessageDto.class);
//        verify(messageService).sendMessage(captor.capture());
//
//        MessageDto sentMessage = captor.getValue();
//        String tokenEncoded = sentMessage.text().split("code=")[1];
//
//        emailConfirmationService.validateConfirmationToken(tokenEncoded);
//
//        verify(authService).confirmByEmail(email);
//    }
//
//    @Test
//    @DisplayName("IT: validateConfirmationToken() should throw when code is invalid or missing")
//    void validateConfirmationToken_shouldThrowException_whenTokenIsInvalid() {
//        UUID fakeToken = UUID.randomUUID();
//        String encoded = Base64.getUrlEncoder().encodeToString(fakeToken.toString().getBytes());
//
//        assertThrows(InvalidConfirmationTokenException.class, () -> emailConfirmationService.validateConfirmationToken(encoded));
//    }
//
//    @Test
//    @DisplayName("IT: validateConfirmationToken() should throw when code is malformed")
//    void validateConfirmationToken_shouldThrow_whenTokenIsMalformed() {
//        String badToken = "not-valid-base64";
//
//        assertThrows(IllegalArgumentException.class, () -> emailConfirmationService.validateConfirmationToken(badToken));
//    }
//
//    @Test
//    @DisplayName("IT: Token should expire after TTL")
//    void tokenShouldExpireAfterTTL() throws Exception {
//        String email = "expire@test.com";
//        emailConfirmationService.sendConfirmationMessage(email);
//
//        ArgumentCaptor<MessageDto> captor = ArgumentCaptor.forClass(MessageDto.class);
//        verify(messageService).sendMessage(captor.capture());
//        String tokenEncoded = captor.getValue().text().split("code=")[1];
//
//        Thread.sleep(11000);
//
//        assertThrows(InvalidConfirmationTokenException.class, () -> emailConfirmationService.validateConfirmationToken(tokenEncoded));
//    }
//}