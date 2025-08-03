package com.aidcompass.auth.domain.account.models.dtos;

import com.aidcompass.auth.domain.account.validation.UniqueEmail;
import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
@Builder
public class DefaultAccountCreateDto {
        @UniqueEmail(message = "Email isn't unique.")
        @Email(message = "Email should be valid.")
        @Size(min = 11, max = 50, message = "Email length must be greater than 11 and less than 50!")
        private final String email;

        @NotBlank(message = "Password is required.")
        @Size(min = 10, max = 50, message = "Password length must be greater than 10 and less than 50!")
        private final String password;

        @JsonIgnore
        private Role role;

        @JsonIgnore
        private List<Authority> authorities;
}