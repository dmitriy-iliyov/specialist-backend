package com.specialist.auth.domain.account.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.specialist.auth.domain.authority.Authority;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class DemodeRequest {
    @JsonIgnore
    private UUID accountId;
    @NotEmpty(message = "Should contains at least one authority.")
    private Set<Authority> authorities;
}
