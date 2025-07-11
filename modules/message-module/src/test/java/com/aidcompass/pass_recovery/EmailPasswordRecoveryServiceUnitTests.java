//package com.aidcompass.recovery;
//
//import com.aidcompass.clients.AuthService;
//import com.aidcompass.exceptions.models.InvalidPasswordRecoveryTokenException;
//import com.aidcompass.message.MessageService;
//import com.aidcompass.message.models.MessageDto;
//import jakarta.mail.MessagingException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.Base64;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class EmailPasswordRecoveryServiceUnitTests {
//
//    private static final String URL = "https://localhost:8443/api/users/password/recover?token=";
//
//    @Mock
//    private MessageService messageService;
//
//    @Mock
//    private PasswordRecoveryRepository passwordRecoveryRepository;
//
//    @Mock
//    private AuthService authService;
//
//    @InjectMocks
//    private EmailPasswordRecoveryService recoveryService;
//
//
//    @Test
//    @DisplayName("UT: sendRecoveryMessage() should send message with encoded code and save it")
//    void sendRecoveryMessage_shouldSendMessage() throws Exception {
//        ReflectionTestUtils.setField(recoveryService, "URL", URL);
//        String email = "test@gmail.com";
//
//        recoveryService.sendRecoveryMessage(email);
//
//        ArgumentCaptor<MessageDto> messageCaptor = ArgumentCaptor.forClass(MessageDto.class);
//        ArgumentCaptor<UUID> tokenCaptor = ArgumentCaptor.forClass(UUID.class);
//
//        verify(messageService).sendMessage(messageCaptor.capture());
//        verify(passwordRecoveryRepository).save(tokenCaptor.capture(), eq(email));
//
//        MessageDto sentMessage = messageCaptor.getValue();
//        UUID code = tokenCaptor.getValue();
//
//        assertEquals(email, sentMessage.recipient());
//        assertTrue(sentMessage.text().contains(URL));
//
//        String encodedToken = Base64.getUrlEncoder().encodeToString(code.toString().getBytes());
//        assertTrue(sentMessage.text().contains(encodedToken));
//
//        verifyNoMoreInteractions(messageService, passwordRecoveryRepository, authService);
//    }
//
//    @Test
//    @DisplayName("UT: recoverPassword() should recover password when code is valid")
//    void recoverPassword_shouldRecoverPassword() {
//        UUID code = UUID.randomUUID();
//        String encodedToken = Base64.getUrlEncoder().encodeToString(code.toString().getBytes());
//        String email = "user@example.com";
//        String newPassword = "newSecurePassword";
//
//        when(passwordRecoveryRepository.findAndDeleteByToken(code)).thenReturn(Optional.of(email));
//
//        recoveryService.recoverPassword(encodedToken, newPassword);
//
//        verify(passwordRecoveryRepository).findAndDeleteByToken(code);
//        verify(authService).recoverPassword(email, newPassword);
//        verifyNoMoreInteractions(passwordRecoveryRepository, authService, messageService);
//    }
//
//    @Test
//    @DisplayName("UT: recoverPassword() should throw exception when code is invalid")
//    void recoverPassword_shouldThrowException_whenTokenInvalid() {
//        UUID code = UUID.randomUUID();
//        String encodedToken = Base64.getUrlEncoder().encodeToString(code.toString().getBytes());
//        String newPassword = "newSecurePassword";
//
//        when(passwordRecoveryRepository.findAndDeleteByToken(code)).thenReturn(Optional.empty());
//
//        assertThrows(InvalidPasswordRecoveryTokenException.class, () ->
//                recoveryService.recoverPassword(encodedToken, newPassword));
//
//        verify(passwordRecoveryRepository).findAndDeleteByToken(code);
//        verifyNoInteractions(authService);
//        verifyNoMoreInteractions(passwordRecoveryRepository, messageService);
//    }
//}