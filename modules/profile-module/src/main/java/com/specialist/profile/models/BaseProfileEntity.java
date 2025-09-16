package com.specialist.profile.models;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.profile.mappers.ProfileTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseProfileEntity {

    @Id
    protected UUID id;

    @Convert(converter = ProfileTypeConverter.class)
    @Column(nullable = false, updatable = false)
    protected ProfileType type;

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
