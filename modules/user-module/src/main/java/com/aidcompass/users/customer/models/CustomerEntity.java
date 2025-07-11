package com.aidcompass.users.customer.models;


import com.aidcompass.users.detail.models.DetailEntity;
import com.aidcompass.users.gender.Gender;
import com.aidcompass.users.gender.GenderConverter;
import com.aidcompass.users.profile_status.models.ProfileStatusEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customers")
public class CustomerEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "second_name", length = 20)
    private String secondName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "birthday_date")
    private LocalDate birthdayDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "detail_id", nullable = false, updatable = false, unique = true)
    private DetailEntity detailEntity;

    @Column(name = "profile_progress")
    private int profileProgress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_status_id", nullable = false)
    private ProfileStatusEntity profileStatusEntity;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    public CustomerEntity(UUID id, ProfileStatusEntity profileStatusEntity, DetailEntity detailEntity) {
        this.id = id;
        this.profileStatusEntity = profileStatusEntity;
        this.detailEntity = detailEntity;
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