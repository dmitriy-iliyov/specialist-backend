package com.aidcompass.core.contact.core.models.entity;

import com.aidcompass.core.contact.contact_type.models.ContactTypeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "contacts")
@Data
@Accessors(fluent = false)
@AllArgsConstructor
@NoArgsConstructor
public class ContactEntity {

    @Id
    @SequenceGenerator(name = "cont_seq", sequenceName = "cont_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cont_seq")
    private Long id;

    @Column(name = "owner_id", nullable = false, updatable = false)
    private UUID ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private ContactTypeEntity typeEntity;

    @Column(name = "contact", unique = true, nullable = false)
    private String contact;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed;

    @Column(name = "is_linked_to", nullable = false)
    private boolean isLinkedToAccount;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    @PrePersist
    public void prePersist() {
        Instant time = Instant.now();
        this.createdAt = time;
        this.updatedAt = time;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
