package com.aidcompass.user.models;

import com.aidcompass.user.avatar.AvatarEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "second_name", length = 20)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(name = "creator_rating", nullable = false)
    private double creatorRating;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public UserEntity(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        if (secondName == null)
            return lastName + " " + firstName;
        return lastName + " " + firstName + " " + secondName;
    }
}