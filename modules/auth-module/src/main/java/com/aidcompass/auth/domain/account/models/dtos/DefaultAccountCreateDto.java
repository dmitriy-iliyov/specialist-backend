package com.aidcompass.auth.domain.account.models.dtos;

import com.aidcompass.auth.domain.account.validation.UniqueEmail;
import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.role.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
@Builder
public class DefaultAccountCreateDto {
        @UniqueEmail
        @Email(message = "Email should be valid.")
        private final String email;

        @NotBlank(message = "Password is required.")
        private final String password;

        @JsonIgnore
        private Role role;

        @JsonIgnore
        private List<Authority> authorities;
}