package com.aidcompass.message.pass_recovery;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Password recovery",
        description = "Method to password recovery"
)
@RestController
@RequestMapping("/api/password-recovery")
@RequiredArgsConstructor
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;


    @Operation(summary = "Send recovery code to user resource")
    @PostMapping("/request")
    public ResponseEntity<?> passwordRecoveryRequest(@Parameter(description = "user resource")
                                                     @RequestParam("resource")
                                                     @NotBlank(message = "Resource shouldn't be blank or empty!")
                                                     String resource) throws Exception {
        passwordRecoveryService.sendRecoveryMessage(resource);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Reset password by code")
    @PatchMapping("/recover")
    public ResponseEntity<?> setNewPassword(@Parameter(description = "recovery pair")
                                                @RequestBody @Valid RecoveryPair pair) {
        passwordRecoveryService.recoverPassword(pair.code(), pair.password());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
