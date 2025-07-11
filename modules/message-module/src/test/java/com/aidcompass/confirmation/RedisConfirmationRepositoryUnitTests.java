//package com.aidcompass.confirmation;
//
//import com.aidcompass.confirmation.repositories.RedisConfirmationRepository;
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
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class RedisConfirmationRepositoryUnitTests {
//
//    @Mock
//    RedisTemplate<String, String> redisTemplate;
//
//    @InjectMocks
//    RedisConfirmationRepository redisConfirmationRepository;
//
//    private final String CONFIRMATION_TOKEN_KEY_TEMPLATE = "tkn:rsrc:conf:%s";
//
//
//    @Test
//    @DisplayName("UT: save() should generate correct Redis password and store the value with TTL")
//    void save() {
//        ValueOperations<String, String> valueOperationsMock = mock(ValueOperations.class);
//        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
//
//        ReflectionTestUtils.setField(redisConfirmationRepository, "TOKEN_TTL", 300L);
//
//        UUID code = UUID.randomUUID();
//        String resource = "test-resource";
//        String password = CONFIRMATION_TOKEN_KEY_TEMPLATE.formatted(code);
//
//        redisConfirmationRepository.save(code, resource);
//
//        ArgumentCaptor<String> keyArgumentCaptor = ArgumentCaptor.forClass(String.class);
//
//        verify(valueOperationsMock).set(keyArgumentCaptor.capture(), eq(resource), any(Long.class), eq(TimeUnit.SECONDS));
//
//        assertEquals(password, keyArgumentCaptor.getValue());
//
//        verify(redisTemplate, times(1)).opsForValue();
//        verify(valueOperationsMock, times(1)).set(password, resource, 300L, TimeUnit.SECONDS);
//        verifyNoMoreInteractions(redisTemplate, valueOperationsMock);
//    }
//
//    @Test
//    @DisplayName("UT: findAndDeleteByToken() should generate correct Redis password and delete it after read")
//    void findAndDeleteByToken() {
//        ValueOperations<String, String> valueOperationsMock = mock(ValueOperations.class);
//        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
//
//        UUID code = UUID.randomUUID();
//        String password = CONFIRMATION_TOKEN_KEY_TEMPLATE.formatted(code);
//        redisConfirmationRepository.findAndDeleteByToken(code);
//
//        ArgumentCaptor<String> keyArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        verify(valueOperationsMock).getAndDelete(keyArgumentCaptor.capture());
//        assertEquals(password, keyArgumentCaptor.getValue());
//
//        verify(redisTemplate, times(1)).opsForValue();
//        verify(valueOperationsMock, times(1)).getAndDelete(password);
//    }
//
//    @Test
//    @DisplayName("UT: findAndDeleteByToken() should return empty Optional if code not found in Redis")
//    void findAndDeleteByToken_notFound() {
//        ValueOperations<String, String> valueOperationsMock = mock(ValueOperations.class);
//        when(redisTemplate.opsForValue()).thenReturn(valueOperationsMock);
//
//        UUID code = UUID.randomUUID();
//        String password = CONFIRMATION_TOKEN_KEY_TEMPLATE.formatted(code);
//
//        when(valueOperationsMock.getAndDelete(password)).thenReturn(null);
//
//        Optional<String> result = redisConfirmationRepository.findAndDeleteByToken(code);
//
//        assertTrue(result.isEmpty());
//
//        verify(redisTemplate, times(1)).opsForValue();
//        verify(valueOperationsMock, times(1)).getAndDelete(password);
//        verifyNoMoreInteractions(redisTemplate, valueOperationsMock);
//    }
//}