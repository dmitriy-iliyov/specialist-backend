package com.aidcompass.core.security.domain.authority.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorities")
public class AuthorityEntity {

    @Id
    @SequenceGenerator(name = "a_seq", sequenceName = "a_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", unique = true, nullable = false, updatable = false)
    private Authority authority;
}