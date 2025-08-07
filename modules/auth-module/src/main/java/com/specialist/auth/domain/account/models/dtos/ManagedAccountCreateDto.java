package com.specialist.auth.domain.account.models.dtos;

import com.specialist.auth.domain.account.validation.UniqueEmail;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import com.specialist.utils.validation.annotation.ValidEnum;
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
