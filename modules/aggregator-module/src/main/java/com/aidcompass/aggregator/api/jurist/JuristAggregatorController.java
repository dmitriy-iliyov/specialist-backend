package com.aidcompass.aggregator.api.jurist;

import com.aidcompass.contracts.PrincipalDetails;
import com.aidcompass.users.gender.Gender;
import com.aidcompass.core.general.utils.validation.ValidEnum;
import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.users.jurist.specialization.models.JuristType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/aggregator/jurists")
@RequiredArgsConstructor
public class JuristAggregatorController {

    private final JuristAggregatorService service;


    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getPublicProfile(@PathVariable("id") UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPublicProfile(id));
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @GetMapping("/me/profile")
    public ResponseEntity<?> getPrivateProfile(@AuthenticationPrincipal PrincipalDetails principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findPrivateProfile(principal.getUserId()));
    }

    @GetMapping("/cards")
    public ResponseEntity<?> getAllApproved(@RequestParam(value = "page", defaultValue = "0")
                                            @PositiveOrZero(message = "Page should be positive!")
                                            int page,
                                            @RequestParam(value = "size", defaultValue = "10")
                                            @Min(value = 10, message = "Size must be at least 10!")
                                            int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllApproved(page, size));
    }

    @GetMapping("/cards/filter")
    public ResponseEntity<?> getJuristsByTypeAndSpecialization(@RequestParam(value = "type", required = false)
                                                               @ValidEnum(enumClass = JuristType.class,
                                                                          nullable = true,
                                                                          message = "Unsupported jurist type!")
                                                               String type,
                                                               @RequestParam(value = "specialization", required = false)
                                                               @ValidEnum(enumClass = JuristSpecialization.class,
                                                                          nullable = true,
                                                                          message = "Unsupported jurist specialization!")
                                                               String specialization,
                                                               @RequestParam(value = "page", defaultValue = "0")
                                                               @PositiveOrZero(message = "Page should be positive!")
                                                               int page,
                                                               @RequestParam(value = "size", defaultValue = "10")
                                                               @Min(value = 10, message = "Size must be at least 10!")
                                                               int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByTypeAndSpecialization(type, specialization, page, size));
    }

    @GetMapping("/cards/names")
    public ResponseEntity<?> getAllJuristsByNamesCombination(@RequestParam(value = "type", required = false)
                                                             @ValidEnum(enumClass = JuristType.class,
                                                                        nullable = true,
                                                                        message = "Unsupported jurist type!")
                                                             String type,

                                                             @RequestParam(value = "first_name", required = false)
                                                             @Size(min = 2, max = 20,
                                                                     message = "Should has lengths from 2 to 20 characters!")
                                                             @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                     message = "First name should contain only Ukrainian!")
                                                             String firstName,

                                                             @RequestParam(value = "second_name", required = false)
                                                             @Size(min = 2, max = 20,
                                                                     message = "Should has lengths from 2 to 20 characters!")
                                                             @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                     message = "Second name should contain only Ukrainian!")
                                                             String secondName,

                                                             @RequestParam(value = "last_name", required = false)
                                                             @Size(min = 2, max = 20,
                                                                     message = "Should has lengths from 2 to 20 characters!")
                                                             @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$",
                                                                     message = "Last name should contain only Ukrainian!")
                                                             String lastName,

                                                             @RequestParam(value = "page", defaultValue = "0")
                                                             @PositiveOrZero(message = "Page should be positive!")
                                                             int page,
                                                             @RequestParam(value = "size", defaultValue = "10")
                                                             @Min(value = 10, message = "Size must be at least 10!")
                                                             int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByNamesCombination(type, firstName, secondName, lastName, page, size));
    }

    @GetMapping("/cards/gender/{gender}")
    public ResponseEntity<?> getAllByGender(@PathVariable("gender")
                                            @ValidEnum(enumClass = Gender.class, message = "Unsupported gender!")
                                            String gender,
                                            @RequestParam(value = "page", defaultValue = "0")
                                            @PositiveOrZero(message = "Page should be positive!")
                                            int page,
                                            @RequestParam(value = "size", defaultValue = "10")
                                            @Min(value = 10, message = "Size must be at least 10!")
                                            int size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllByGender(Gender.toEnum(gender), page, size));
    }

    @PreAuthorize("hasAuthority('ROLE_JURIST')")
    @DeleteMapping("/me/{password}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principal,
                                    @PathVariable("password")
                                    @NotBlank(message = "Password can't be empty or blank!")
                                    @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
                                    String password,
                                    HttpServletRequest request, HttpServletResponse response) {
        service.delete(principal.getUserId(), password, request, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}