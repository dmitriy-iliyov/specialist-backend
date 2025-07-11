package com.aidcompass.core.security.domain.user.models.dto;

import com.aidcompass.core.security.domain.authority.models.Authority;
import com.aidcompass.core.security.domain.user.validation.UserUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@UserUpdate
public class SystemUserUpdateDto {

    @NotNull(message = "Id shouldn't be null!")
    private UUID id;

    @NotBlank(message = "Email shouldn't be empty or blank!")
    @Size(min = 11, max = 50, message = "Email length must be greater than 11 and less than 50!")
    @Email(message = "Email should be valid!")
    private String email;

    @NotBlank(message = "Password can't be empty or blank!")
    @Size(min = 10, max = 22, message = "Password length must be greater than 10 and less than 22!")
    private String password;

    private List<Authority> authorities;
}
