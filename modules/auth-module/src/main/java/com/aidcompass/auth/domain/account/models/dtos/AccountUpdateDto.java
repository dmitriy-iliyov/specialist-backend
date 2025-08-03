package com.aidcompass.auth.domain.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class AccountUpdateDto {

    @JsonIgnore
    private UUID id;

    @NotBlank(message = "Password is required.")
    @Size(min = 10, max = 50, message = "Password length must be greater than 10 and less than 50!")
    private final String password;
}
