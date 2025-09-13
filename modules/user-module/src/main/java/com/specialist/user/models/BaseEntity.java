package com.specialist.user.models;

import com.specialist.contracts.user.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    protected UUID id;

    @Column(nullable = false, updatable = false)
    protected UserType type;

    @Column(nullable = false, name = "first_name", length = 30)
    protected String firstName;

    @Column(name = "second_name", length = 30)
    protected String secondName;

    @Column(nullable = false, name = "last_name", length = 30)
    protected String lastName;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(name = "avatar_url")
    protected String avatarUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    protected Instant updatedAt;

    public String getFullName() {
        if (secondName == null)
            return lastName + " " + firstName;
        return lastName + " " + firstName + " " + secondName;
    }
}
