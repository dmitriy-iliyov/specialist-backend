package com.specialist.auth.domain.account.models.dtos;

import com.specialist.auth.domain.account.validation.UniqueEmail;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.role.Role;
import com.specialist.utils.validation.annotation.ValidEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ManagedAccountCreateDto(
        @UniqueEmail
        @Email(message = "Email should be valid.")
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 10, max = 50, message = "Password length must be greater than 10 and less than 50!")
        String password,

        Role role,

        List<Authority> authorities
) { }
