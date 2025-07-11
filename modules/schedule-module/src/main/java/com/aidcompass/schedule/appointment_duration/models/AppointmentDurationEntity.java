package com.aidcompass.schedule.appointment_duration.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Table(name = "appointment_durations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDurationEntity {

    @Id
    @SequenceGenerator(name = "appointment_duration_seq", sequenceName = "appointment_duration_seq", initialValue = 1, allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_duration_seq")
    private Long id;

    @Column(name = "owner_id", nullable = false, unique = true, updatable = false)
    private UUID ownerId;

    @Column(name = "appointment_duration", nullable = false)
    private Long appointmentDuration;


    public AppointmentDurationEntity(UUID ownerId, Long appointmentDuration) {
        this.ownerId = ownerId;
        this.appointmentDuration = appointmentDuration;
    }
}
