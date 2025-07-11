package com.aidcompass.message.confirmation;


import com.aidcompass.contracts.AccountResourceConfirmationService;
import com.aidcompass.contracts.ContactConfirmationFacade;
import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.general.utils.validation.ValidEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/confirmations")
@RequiredArgsConstructor
public class ConfirmationController {

    private final ContactConfirmationFacade contactConfFacade;
    private final AccountResourceConfirmationService accountConfService;


    @Operation(summary = "Request confirmation code.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Token successfully created."),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input.")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_DOCTOR', 'ROLE_JURIST', 'ROLE_CUSTOMER')")
    @PostMapping("/request")
    public ResponseEntity<?> getToken(@Parameter(description = "resource")
                                      @RequestParam("resource")
                                      @NotBlank(message = "Resource shouldn't be blank or empty!")
                                      String resource,

                                      @Parameter(description = "resource_id")
                                      @RequestParam("resource_id")
                                      @Positive(message = "Resource id should be positive!")
                                      Long resourceId,

                                      @Parameter(description = "resource_type")
                                      @RequestParam("resource_type")
                                      @ValidEnum(enumClass = ContactType.class, message = "Resource type should be valid!")
                                      String type) throws Exception {
        contactConfFacade.sendConfirmationMessage(resource, resourceId, ContactType.valueOf(type));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }


    @Operation(summary = "Confirm resource by code.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Token successfully validated."),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid code or resource type.")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_DOCTOR', 'ROLE_JURIST', 'ROLE_CUSTOMER')")
    @PostMapping("/resource")
    public ResponseEntity<?> confirmResource(@Parameter(description = "code")
                                             @RequestParam("token")
                                             @NotBlank(message = "Token shouldn't be blank or empty!")
                                             String token,

                                             @Parameter(description = "resource_type")
                                             @RequestParam("resource_type")
                                             @ValidEnum(enumClass = ContactType.class, message = "Resource type should be valid!")
                                             String type) {
        contactConfFacade.validateConfirmationToken(token, ContactType.valueOf(type));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Request confirmation code for linked email")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Token successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid email format")
    })
    @PostMapping("/linked-email/request")
    public ResponseEntity<?> getTokenForLinkedEmail(@Parameter(description = "email")
                                                    @RequestParam("email")
                                                    @NotBlank(message = "Email shouldn't be blank or empty!")
                                                    @Email(message = "Email should be valid!") String email) throws Exception {
        accountConfService.sendConfirmationMessage(email);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(
            summary = "Confirm linked email by code",
            description = "Validates the confirmation code for a linked email."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "303", description = "Code successfully validated "),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid code")
    })
    @PostMapping("/linked-email")
    public ResponseEntity<?> confirmLinkedEmail(@Parameter(description = "code")
                                                @RequestParam("code")
                                                @NotBlank(message = "Token shouldn't be blank or empty!") String code) {
        accountConfService.validateConfirmationToken(code);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}

