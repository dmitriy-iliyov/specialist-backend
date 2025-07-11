package com.aidcompass.users.doctor.models;

import com.aidcompass.users.detail.models.DetailEntity;
import com.aidcompass.users.doctor.specialization.models.DoctorSpecializationEntity;
import com.aidcompass.users.gender.Gender;
import com.aidcompass.users.gender.GenderConverter;
import com.aidcompass.users.profile_status.models.ProfileStatusEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"specializations", "profileStatusEntity", "detailEntity"})
@EqualsAndHashCode(exclude = {"specializations", "profileStatusEntity", "detailEntity"})
public class DoctorEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 20)
    private String secondName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_specialization_relations",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private List<DoctorSpecializationEntity> specializations;

    @Column(name = "specialization_detail", nullable = false)
    private String specializationDetail;

    @Column(name = "working_experience", nullable = false, length = 2)
    private Integer workingExperience;

    @Column(nullable = false)
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "is_approved", nullable = false)
    private boolean isApproved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_status_id", nullable = false)
    private ProfileStatusEntity profileStatusEntity;

    @Column(name = "profile_progress", nullable = false)
    private int profileProgress;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "detail_id", nullable = false, updatable = false, unique = true)
    private DetailEntity detailEntity;

    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


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
