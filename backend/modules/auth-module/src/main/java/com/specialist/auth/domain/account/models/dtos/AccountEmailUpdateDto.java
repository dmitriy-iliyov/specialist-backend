package com.specialist.auth.domain.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.specialist.auth.domain.account.validation.UniqueEmail;
import com.specialist.contracts.profile.ProfileType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountEmailUpdateDto {

    @JsonIgnore
    private UUID id;

    @JsonIgnore
    private ProfileType type;

    @NotBlank(message = "Password is required.")
    @Size(min = 10, max = 50, message = "Password length must be greater than 10 and less than 50!")
    private final String password;

    @Email(message = "Email should be valid.")
    @Size(min = 11, max = 50, message = "Email length must be greater than 11 and less than 50!")
    private final String email;
}
