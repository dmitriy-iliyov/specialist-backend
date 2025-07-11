//package com.aidcompass.recovery;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.Optional;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class RedisPasswordRecoveryRepositoryUnitTests {
//
//    @Mock
//    RedisTemplate<String, String> redisTemplate;
//
//    @InjectMocks
//    RedisPasswordRecoveryRepository redisPasswordRecoveryRepository;
//
//    private final String RECOVERY_TOKEN_KEY_TEMPLATE = "tkn:pass:recov:%s";
//
//    @Test
//    @DisplayName("UT: save() should generate correct Redis password and store the value with TTL")
//    void save() {
//        ValueOperations<String, String> valueOperationsMock = mock(ValueOperations.class);
//        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
//
//        ReflectionTestUtils.setField(redisPasswordRecoveryRepository, "TOKEN_TTL", 600L);
//
//        UUID code = UUID.randomUUID();
//        String resource = "recovery@example.com";
//        String password = RECOVERY_TOKEN_KEY_TEMPLATE.formatted(code);
//
//        redisPasswordRecoveryRepository.save(code, resource);
//
//        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
//
//        verify(valueOperationsMock).set(keyCaptor.capture(), eq(resource), eq(600L), eq(TimeUnit.SECONDS));
//        assertEquals(password, keyCaptor.getValue());
//
//        verify(redisTemplate, times(1)).opsForValue();
//        verify(valueOperationsMock, times(1)).set(password, resource, 600L, TimeUnit.SECONDS);
//        verifyNoMoreInteractions(redisTemplate, valueOperationsMock);
//    }
//
//    @Test
//    @DisplayName("UT: findAndDeleteByToken() should return value from Redis and delete the password")
//    void findAndDeleteByToken() {
//        ValueOperations<String, String> valueOperationsMock = mock(ValueOperations.class);
//        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
//
//        UUID code = UUID.randomUUID();
//        String expectedValue = "recovery@example.com";
//        String password = RECOVERY_TOKEN_KEY_TEMPLATE.formatted(code);
//
//        when(valueOperationsMock.getAndDelete(password)).thenReturn(expectedValue);
//
//        Optional<String> result = redisPasswordRecoveryRepository.findAndDeleteByToken(code);
//
//        assertTrue(result.isPresent());
//        assertEquals(expectedValue, result.get());
//
//        verify(redisTemplate, times(1)).opsForValue();
//        verify(valueOperationsMock, times(1)).getAndDelete(password);
//        verifyNoMoreInteractions(redisTemplate, valueOperationsMock);
//    }
//
//    @Test
//    @DisplayName("UT: findAndDeleteByToken() should return empty Optional if code not found in Redis")
//    void findAndDeleteByToken_notFound() {
//        ValueOperations<String, String> valueOperationsMock = mock(ValueOperations.class);
//        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
//
//        UUID code = UUID.randomUUID();
//        String password = RECOVERY_TOKEN_KEY_TEMPLATE.formatted(code);
//
//        when(valueOperationsMock.getAndDelete(password)).thenReturn(null);
//
//        Optional<String> result = redisPasswordRecoveryRepository.findAndDeleteByToken(code);
//
//        assertTrue(result.isEmpty());
//
//        verify(redisTemplate, times(1)).opsForValue();
//        verify(valueOperationsMock, times(1)).getAndDelete(password);
//        verifyNoMoreInteractions(redisTemplate, valueOperationsMock);
//    }
//}