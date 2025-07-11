//package com.aidcompass.recovery;
//
//import com.aidcompass.exceptions.MessageModuleControllerAdvice;
//import com.aidcompass.exceptions.models.InvalidPasswordRecoveryTokenException;
//import com.aidcompass.mapper.ExceptionMapperImpl;
//import io.lettuce.core.RedisConnectionException;
//import jakarta.mail.MessagingException;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.mail.MailAuthenticationException;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(PasswordRecoveryController.class)
//@Import({MessageModuleControllerAdvice.class, ExceptionMapperImpl.class})
//@ContextConfiguration(classes = {PasswordRecoveryController.class})
//public class PasswordRecoveryControllerIntegrationTests {
//
//    @MockitoBean
//    PasswordRecoveryService passwordRecoveryService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    @DisplayName("IT: passwordRecoveryRequest() should return CREATED")
//    void passwordRecovery_Request_shouldReturnCreated() throws Exception {
//        String resource = "resource-example";
//
//        doNothing().when(passwordRecoveryService).sendRecoveryMessage(resource);
//
//        mockMvc.perform(post("/api/password-recovery/request")
//                        .param("resource", resource)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//
//        verify(passwordRecoveryService, times(1)).sendRecoveryMessage(resource);
//        verifyNoMoreInteractions(passwordRecoveryService);
//    }
//
//    @Test
//    @DisplayName("IT: recoverPassword() should return NO_CONTENT on valid code")
//    void recoverPassword_whenTokenValid_shouldReturnNoContent() throws Exception {
//        String code = "valid-code";
//        String password = "new-password";
//
//        doNothing().when(passwordRecoveryService).recoverPassword(code, password);
//
//        mockMvc.perform(patch("/api/password-recovery/recover")
//                        .param("code", code)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"password\": \"" + password + "\"}"))
//                .andExpect(status().isNoContent());
//
//        verify(passwordRecoveryService, times(1)).recoverPassword(code, password);
//        verifyNoMoreInteractions(passwordRecoveryService);
//    }
//
//    @Test
//    @DisplayName("IT: recoverPassword() should return BAD_REQUEST when code is invalid")
//    void recoverPassword_whenTokenInvalid_shouldReturnBadRequest() throws Exception {
//        String code = "invalid-code";
//        String password = "new-password";
//
//        Mockito.doThrow(new InvalidPasswordRecoveryTokenException()).when(passwordRecoveryService).recoverPassword(code, password);
//
//        mockMvc.perform(patch("/api/password-recovery/recover")
//                        .param("code", code)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"password\": \"" + password + "\"}"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Recovery code is invalid!"));
//
//        verify(passwordRecoveryService, times(1)).recoverPassword(code, password);
//        verifyNoMoreInteractions(passwordRecoveryService);
//    }
//
//    @Test
//    @DisplayName("IT: passwordRecoveryRequest() when redis disconnected should return 500")
//    void passwordRecovery_Request_whenRedisDisconnected_shouldReturn500() throws Exception {
//        String resource = "resource-example";
//
//        doThrow(new RedisConnectionException("Redis disconnected")).when(passwordRecoveryService)
//                .sendRecoveryMessage(resource);
//
//        mockMvc.perform(post("/api/password-recovery/request")
//                        .param("resource", resource)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isInternalServerError());
//
//        verify(passwordRecoveryService, times(1)).sendRecoveryMessage(resource);
//        verifyNoMoreInteractions(passwordRecoveryService);
//    }
//
//    @Test
//    @DisplayName("IT: recoverPassword() when redis disconnected should return 500")
//    void recoverPassword_whenRedisDisconnected_shouldReturn500() throws Exception {
//        String code = "code-example";
//        String password = "new-password";
//
//        doThrow(new RedisConnectionException("Redis disconnected")).when(passwordRecoveryService)
//                .recoverPassword(code, password);
//
//        mockMvc.perform(patch("/api/password-recovery/recover")
//                        .param("code", code)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"password\": \"" + password + "\"}"))
//                .andExpect(status().isInternalServerError());
//
//        verify(passwordRecoveryService, times(1)).recoverPassword(code, password);
//        verifyNoMoreInteractions(passwordRecoveryService);
//    }
//
//    @Test
//    @DisplayName("IT: requestToPasswordRecovery() when trouble with company mail should return 500")
//    void passwordRecovery_Request_whenMailException_shouldReturn500() throws Exception {
//        String resource = "resource-example";
//
//        doThrow(new MailAuthenticationException("Mail exception")).when(passwordRecoveryService)
//                .sendRecoveryMessage(resource);
//
//        mockMvc.perform(post("/api/password-recovery/request")
//                        .param("resource", resource)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Sorry, problems with our email!"));
//
//        verify(passwordRecoveryService, times(1)).sendRecoveryMessage(resource);
//        verifyNoMoreInteractions(passwordRecoveryService);
//    }
//
//    @Test
//    @DisplayName("IT: getToken() when trouble with company mail should return 500")
//    void getToken_whenMessageException_shouldReturn500() throws Exception {
//        String resource = "resource-example";
//
//        doThrow(new MessagingException("test message exception")).when(passwordRecoveryService)
//                .sendRecoveryMessage(resource);
//
//        mockMvc.perform(post("/api/password-recovery/request")
//                        .param("resource", resource)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Error when sending email, please try again!"));
//
//        verify(passwordRecoveryService, times(1)).sendRecoveryMessage(resource);
//        verifyNoMoreInteractions(passwordRecoveryService);
//    }
//}