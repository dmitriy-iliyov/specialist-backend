package com.aidcompass.core.contact.contact_filling_progress.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "contact_progreses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactProgressEntity {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "filled_email")
    private boolean filledEmail;

    @Column(name = "filled_phone")
    private boolean filledPhone;

    @Transient
    private boolean wasComplete;


    public ContactProgressEntity(UUID userId) {
        this.userId = userId;
    }

    @PostLoad
    public void postLoad() {
        this.setWasComplete(this.isFilledEmail() && this.isFilledPhone());
    }
}
