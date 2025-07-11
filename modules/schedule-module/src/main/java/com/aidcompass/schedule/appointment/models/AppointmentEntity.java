package com.aidcompass.schedule.appointment.models;

import com.aidcompass.schedule.appointment.models.enums.AppointmentStatus;
import com.aidcompass.schedule.appointment.models.enums.AppointmentType;
import com.aidcompass.schedule.appointment.repositories.AppointmentStatusConverter;
import com.aidcompass.schedule.appointment.repositories.AppointmentTypeConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @SequenceGenerator(name = "app_seq", sequenceName = "app_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_seq")
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "volunteer_id", nullable = false, updatable = false)
    private UUID volunteerId;

    @Column(name = "start_t", nullable = false)
    private LocalTime start;

    @Column(name = "end_t", nullable = false)
    private LocalTime end;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Convert(converter = AppointmentTypeConverter.class)
    private AppointmentType type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(length = 1000)
    private String review;

    @Column(nullable = false)
    @Convert(converter = AppointmentStatusConverter.class)
    private AppointmentStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;


    @PrePersist
    public void beforePersist(){
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void beforeUpdate(){
        updatedAt = Instant.now();
    }
}
