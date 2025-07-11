//package com.aidcompass.recovery;
//
//import jakarta.mail.MessagingException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.doNothing;
//
//@ExtendWith(MockitoExtension.class)
//public class PasswordRecoveryControllerUnitTests {
//
//    @Mock
//    private PasswordRecoveryService passwordRecoveryService;
//
//    @InjectMocks
//    private PasswordRecoveryController passwordRecoveryController;
//
//    @Test
//    @DisplayName("UT: passwordRecoveryRequest() should return status CREATED when recovery message is sent")
//    void passwordRecovery_Request_shouldReturnCreated() throws Exception {
//        String resource = "test@example.com";
//
//        doNothing().when(passwordRecoveryService).sendRecoveryMessage(resource);
//
//        ResponseEntity<?> response = passwordRecoveryController.passwordRecoveryRequest(resource);
//
//        verify(passwordRecoveryService, times(1)).sendRecoveryMessage(resource);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("UT: setNewPassword() should return status NO_CONTENT when password is successfully updated")
//    void setNewPassword_shouldReturnNoContent() {
//        String code = "valid-code";
//        String password = "newPassword123";
//
//        doNothing().when(passwordRecoveryService).recoverPassword(code, password);
//
//        ResponseEntity<?> response = passwordRecoveryController.setNewPassword(code, Map.of("password", password));
//
//        verify(passwordRecoveryService, times(1)).recoverPassword(code, password);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }
//}