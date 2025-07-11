//package com.aidcompass.confirmation;
//
//import com.aidcompass.confirmation.services.ResourceConfirmationService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.net.URI;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//
//@ExtendWith(MockitoExtension.class)
//public class ConfirmationControllerUnitTests {
//
//    @Mock
//    ResourceConfirmationService resourceConfirmationService;
//
//    @InjectMocks
//    ConfirmationController confirmationController;
//
//    @DisplayName("UT: confirmEmail() should validate code and return 303 redirect to /users/login")
//    @Test
//    void confirmEmail_shouldValidateTokenAndRedirect() {
//        String code = "test-code";
//
//        ResponseEntity<?> response = confirmationController.confirmLinkedEmail(code);
//
//        verify(resourceConfirmationService, times(1)).validateConfirmationToken(code);
//        assertEquals(HttpStatus.SEE_OTHER, response.getStatusCode());
//        assertEquals(URI.create("/api/users/login"), response.getHeaders().getLocation());
//        assertNull(response.getBody());
//    }
//
//    @Test
//    @DisplayName("UT: getToken() should send conf message")
//    void getToken() throws Exception {
//        String email = "test@gmail.com";
//
//        ResponseEntity<?> response = confirmationController.getTokenForLinkedEmail(email);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    }
//}
