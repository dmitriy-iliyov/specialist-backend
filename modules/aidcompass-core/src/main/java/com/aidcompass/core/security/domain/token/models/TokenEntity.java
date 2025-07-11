package com.aidcompass.core.security.domain.token.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deactivated_auth_token")
public class TokenEntity {

    @Id
    private UUID id;

    @Column(name = "deactivate_at", nullable = false)
    private Instant deactivateAt;

}
