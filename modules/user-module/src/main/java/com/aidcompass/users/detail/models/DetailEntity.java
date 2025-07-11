package com.aidcompass.users.detail.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "user_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailEntity {

    @Id
    @SequenceGenerator(name = "detail_seq", sequenceName = "detail_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detail_seq")
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "about_myself", length = 235)
    private String aboutMyself;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public DetailEntity(UUID userId) {
        this.userId = userId;
    }

    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = Instant.now();
    }
}
