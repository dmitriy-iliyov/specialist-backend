//package com.aidcompass.confirmation;
//
//import com.aidcompass.contracts.AccountResourceConfirmationService;
//import com.aidcompass.exceptions.MessageModuleControllerAdvice;
//import com.aidcompass.exceptions.models.InvalidConfirmationTokenException;
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
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(ConfirmationController.class)
//@Import({MessageModuleControllerAdvice.class, ExceptionMapperImpl.class})
//@ContextConfiguration(classes = {ConfirmationController.class})
//public class ConfirmationControllerIntegrationTests {
//
//    @MockitoBean
//    AccountResourceConfirmationService accountResourceConfirmationService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//
//    @Test
//    @DisplayName("IT: confirmEmail() should redirect to /users/login on valid code")
//    void confirmEmail_whenTokenValid_shouldReturnSeeOther() throws Exception {
//        String code = "code-example";
//
//        doNothing().when(accountResourceConfirmationService).validateConfirmationToken(code);
//
//        mockMvc.perform(post("/api/confirm/email")
//                        .param("code", code)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isSeeOther())
//                .andExpect(header().string("Location", "/api/users/login"));
//
//        verify(accountResourceConfirmationService, times(1)).validateConfirmationToken(code);
//        verifyNoMoreInteractions(accountResourceConfirmationService);
//    }
//
//    @Test
//    @DisplayName("IT: confirmEmail() should return BAD_REQUEST")
//    void confirmEmail_whenTokenValid_shouldReturnNotFound() throws Exception {
//        String code = "code-example";
//
//        Mockito.doThrow(new InvalidConfirmationTokenException()).when(accountResourceConfirmationService).validateConfirmationToken(code);
//
//        mockMvc.perform(post("/api/confirm/email")
//                        .param("code", code)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Confirmation code is invalid!"));
//
//        verify(accountResourceConfirmationService, times(1)).validateConfirmationToken(code);
//        verifyNoMoreInteractions(accountResourceConfirmationService);
//    }
//
//    @Test
//    @DisplayName("IT: getToken() should return CREATED")
//    void getToken_shouldReturnCreated() throws Exception {
//        String resource = "resource-example";
//
//        doNothing().when(accountResourceConfirmationService).sendConfirmationMessage(resource);
//
//        mockMvc.perform(post("/api/confirm/request")
//                        .param("resource", resource)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//
//        verify(accountResourceConfirmationService, times(1)).sendConfirmationMessage(resource);
//        verifyNoMoreInteractions(accountResourceConfirmationService);
//    }
//
//    @Test
//    @DisplayName("IT: getToken() when redis disconnected should return 500")
//    void getToken_whenRedisDisconnected_shouldReturn500() throws Exception {
//        String resource = "resource-example";
//
//        doThrow(new RedisConnectionException("test redis disconnected message")).when(accountResourceConfirmationService)
//                .sendConfirmationMessage(resource);
//
//        mockMvc.perform(post("/api/confirm/request")
//                        .param("resource", resource)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isInternalServerError());
//
//        verify(accountResourceConfirmationService, times(1)).sendConfirmationMessage(resource);
//        verifyNoMoreInteractions(accountResourceConfirmationService);
//    }
//
//    @Test
//    @DisplayName("IT: confirmEmail() when redis disconnected should return 500")
//    void confirmEmail_whenRedisDisconnected_shouldReturn500() throws Exception {
//        String code = "code-example";
//
//        doThrow(new RedisConnectionException("test message")).when(accountResourceConfirmationService).validateConfirmationToken(code);
//
//        mockMvc.perform(post("/api/confirm/email")
//                        .param("code", code)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isInternalServerError());
//
//        verify(accountResourceConfirmationService, times(1)).validateConfirmationToken(code);
//        verifyNoMoreInteractions(accountResourceConfirmationService);
//    }
//
//    @Test
//    @DisplayName("IT: getToken() when trouble with company mail should return 500")
//    void getToken_whenMailException_shouldReturn500() throws Exception {
//        String resource = "resource-example";
//
//        doThrow(new MailAuthenticationException("test mail exception message")).when(accountResourceConfirmationService)
//                .sendConfirmationMessage(resource);
//
//        mockMvc.perform(post("/api/confirm/request")
//                        .param("resource", resource)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Sorry, problems with our email!"));
//
//        verify(accountResourceConfirmationService, times(1)).sendConfirmationMessage(resource);
//        verifyNoMoreInteractions(accountResourceConfirmationService);
//    }
//
//    @Test
//    @DisplayName("IT: getToken() when trouble with company mail should return 500")
//    void getToken_whenMessageException_shouldReturn500() throws Exception {
//        String resource = "resource-example";
//
//        doThrow(new MessagingException("test message exception")).when(accountResourceConfirmationService)
//                .sendConfirmationMessage(resource);
//
//        mockMvc.perform(post("/api/confirm/request")
//                        .param("resource", resource)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.properties.errors[0].message")
//                        .value("Error when sending email, please try again!"));
//
//        verify(accountResourceConfirmationService, times(1)).sendConfirmationMessage(resource);
//        verifyNoMoreInteractions(accountResourceConfirmationService);
//    }
//}
