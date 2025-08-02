package com.aidcompass.auth.domain.account.models.dtos;

import com.aidcompass.auth.domain.account.validation.UniqueEmail;
import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.role.Role;
import com.aidcompass.utils.validation.annotation.ValidEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ManagedAccountCreateDto(
        @UniqueEmail
        @Email(message = "Email should be valid.")
        String email,

        @NotBlank(message = "Password is required.")
        String password,

        @ValidEnum(enumClass = Role.class, message = "Unsupported role.")
        String role,

        @Valid
        List<@ValidEnum(enumClass = Authority.class, message = "Unsupported authority.") String> authorities
) { }
