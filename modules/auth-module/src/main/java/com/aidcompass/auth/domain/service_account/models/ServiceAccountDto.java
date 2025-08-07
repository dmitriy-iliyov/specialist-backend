package com.aidcompass.auth.domain.service_account.models;

import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.auth.domain.role.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ServiceAccountDto {
    private UUID id;
    @NotBlank(message = "Secret is required.")
    private String secret;
    private Role role;
    @NotEmpty(message = "At list one authority is required.")
    private Set<Authority> authorities;
}
